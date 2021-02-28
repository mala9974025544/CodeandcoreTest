package com.codeandcore.test.ui

import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import com.codeandcore.test.R
import com.codeandcore.test.datamodels.ConfigurableOption
import com.codeandcore.test.datamodels.Request
import com.codeandcore.test.datamodels.Response
import com.codeandcore.test.services.ApiClient
import com.codeandcore.test.utils.Utils
import com.codeandcore.test.viewmodels.ShopingViewModel
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.stylist.services.Status
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dash_banner.*
import kotlinx.android.synthetic.main.toolbar.*


class MainActivity : BaseActivity(), View.OnClickListener, OrderItemSuggestionBottomsheet.ListnerForUpdateQty {

    private lateinit var shopingViewModel: ShopingViewModel
    var productId :String= "124"
    var attibuteBuffer=StringBuffer()
    var optionBuffer=StringBuffer()
    lateinit var  bottomDialogFragment : OrderItemSuggestionBottomsheet
    lateinit var configurableOptionList: ArrayList<ConfigurableOption>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
        getShoppingData()
        initViews()

    }

    private fun initViews() {
      btnAddBag.setOnClickListener(this)
    }

    private fun setupViewModel() {
        shopingViewModel =
            ViewModelProviders.of(this, ShopingViewModel.ViewModelFactory(ApiClient().apiService))
                .get(ShopingViewModel::class.java)
    }

    private fun getShoppingData() {
        var lang = "en"
        var userId = "46"
        var store = "KW"
        shopingViewModel.getHoliday(productId = productId).observe(this) {
            it.let { resource ->
                when (resource.status) {
                    Status.SUCESS -> {
                        stopAnim()
                        if (resource.data?.isSuccessful!!) {
                            resource.data.let { it1 ->
                                setupUI(resource.data.body())
                            }
                        } else {
                            Utils.showToast(this, it.message.toString(), Toast.LENGTH_LONG)
                        }

                    }
                    Status.ERROR -> {
                        stopAnim()
                        Utils.showToast(this, it.message.toString(), Toast.LENGTH_LONG)
                    }
                    Status.LOADING -> {
                        startAnim()

                    }

                }
            }
        }
    }
    private fun addToBag(request: Request,attrId : String?) {
        shopingViewModel.addTobeg(request).observe(this) {
            it.let { resource ->
                when (resource.status) {
                    Status.SUCESS -> {
                        bottomDialogFragment.stopAnim()
                        if (resource.data?.isSuccessful!!) {
                            resource.data.let { it1 ->
                                attrId?.let { it2 -> bottomDialogFragment.notifyUI(resource.data.body(), it2) }
                            }
                        } else {
                            Utils.showToast(this, it.message.toString(), Toast.LENGTH_LONG)
                        }

                    }
                    Status.ERROR -> {
                        bottomDialogFragment.stopAnim()
                        Utils.showToast(this, it.message.toString(), Toast.LENGTH_LONG)
                    }
                    Status.LOADING -> {
                        bottomDialogFragment.startAnim()

                    }

                }
            }
        }
    }

    private fun setupUI(response: Response?) {
        if (response != null) {
            nScrolHoliday.visibility=View.VISIBLE
            setBannerData(response)
            btnDiscount.visibility = View.VISIBLE

            if (response.data != null) {

                if (!response.data?.name.isNullOrEmpty())
                    tvTitle.text = response.data?.name

                if (!response.data?.name.isNullOrEmpty())
                    tvMainTitle.text = response.data?.name


                if (!response.data?.average_rating.isNullOrEmpty()) {
                    //tvRate.text=response.data?.average_rating
                    tvRate.text = "4120"
                }

                if (!response.data?.currency_code.isNullOrEmpty()) {
                    var currency = StringBuffer()
                    currency.append(response.data?.currency_code)
                    currency.append(" ")
                    currency.append(response.data?.final_price)
                    tvCurrency.text = currency.toString()

                    var stringBuffer = StringBuffer()
                    stringBuffer.append(response.data?.currency_code)
                    stringBuffer.append(" ")
                    stringBuffer.append(response.data?.regular_price)
                    tvPrice.text = stringBuffer.toString()

                    tvPrice.paintFlags = tvPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

                }
                if (!response.data?.SKU.isNullOrEmpty()) {
                    tvSize.text = String.format(getString(R.string.txt_kwd), response.data?.SKU)
                }

                if (!response.data?.boutique_name.isNullOrEmpty()) {
                    tvSizeType.text = response.data?.boutique_name
                }
                setSpanString(
                    getString(R.string.txt_fulfill),
                    getString(R.string.txt_yashMaal),
                    tvBrand
                )

                if (!response.data?.description.isNullOrEmpty()) {
                    tvDes.text = response.data?.description
                    tvDes.setShowingLine(2)
                    tvDes.addShowMoreText(getString(R.string.txt_read_more))
                    tvDes.addShowLessText(getString(R.string.read_less))

                }

                btnDiscount.text = "17 % OFF"

                if(!response?.data?.configurable_option.isNullOrEmpty())
                configurableOptionList=response?.data?.configurable_option!!
            }
        }
    }

    private fun setBannerData(response: Response?) {
        if (response != null) {
            var data = response.data
            if (data != null) {

                if (!data.images.isNullOrEmpty())
                    imageSlider.setSliderAdapter(SliderAdapterExample(this, data.images))
                imageSlider.startAutoCycle()
                imageSlider.indicatorUnselectedColor =
                    ContextCompat.getColor(this, R.color.colorWhite)
                imageSlider.setIndicatorAnimation(IndicatorAnimationType.SLIDE)
                imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
                imageSlider.scrollTimeInSec = data.images!!.size
            }
        }
    }

    private fun setSpanString(string1: String, string2: String, textView: TextView) {
        val builder = SpannableStringBuilder()
        builder.append(string1)
        builder.append(" ")
        val txtSpannable = SpannableString(string2)
        val boldSpan = StyleSpan(Typeface.BOLD)
        txtSpannable.setSpan(boldSpan, 0, string2.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)


        builder.append(txtSpannable)
        textView.setText(builder, TextView.BufferType.SPANNABLE)
    }

    private fun startAnim() {
        if (avLoading != null) {
            avLoading.show()
            avLoading.visibility = View.VISIBLE
        }

    }

    private fun stopAnim() {
        if (avLoading != null) {
            avLoading.hide()
            avLoading.visibility = View.GONE
        }

    }

    override fun onClick(view: View?) {
        when(view?.id ){
            R.id.btnAddBag->{
                if (!configurableOptionList.isEmpty()) {
                     bottomDialogFragment =
                            OrderItemSuggestionBottomsheet(configurableOptionList)
                    bottomDialogFragment.setAddToCartLister(this)
                    bottomDialogFragment.show(supportFragmentManager, "BottomDialogFragment")
                }
        }
        }
    }

    override fun updateQty(configurableOption: ConfigurableOption, attrId: String?) {
        var request= Request()

        if(attibuteBuffer.isNotEmpty())
        attibuteBuffer.append(",")

        attibuteBuffer.append(configurableOption.attribute_id)

        if(optionBuffer.isNotEmpty())
        optionBuffer.append(",")

        optionBuffer.append(configurableOption.attributes?.get(0)?.option_id)

        request.attribute_id=attibuteBuffer.toString()

        request.option_id=optionBuffer.toString()

        request.product_id=productId


       addToBag(request,attrId)
    }


}

