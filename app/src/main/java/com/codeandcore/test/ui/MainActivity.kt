package com.codeandcore.test.ui

import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.codeandcore.test.R
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

class MainActivity : BaseActivity() {

    private lateinit var shopingViewModel: ShopingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
        getShoppingData()


    }

    private fun setupViewModel() {
        shopingViewModel =
            ViewModelProviders.of(this, ShopingViewModel.ViewModelFactory(ApiClient().apiService))
                .get(ShopingViewModel::class.java)
    }

    private fun getShoppingData() {

        var productId = "124"
        var lang = "en"
        var userId = "46"
        var store = "KW"
        shopingViewModel.getHoliday(productId = productId).observe(this) {
            it.let { resource ->
                when (resource.status) {
                    Status.SUCESS -> {
                        if (resource.data?.isSuccessful!!) {
                            resource.data.let { it1 ->
                                setupUI(resource.data.body())
                            }
                        } else {
                            Utils.showToast(this, it.message.toString(), Toast.LENGTH_LONG)
                        }

                    }
                    Status.ERROR -> {

                        Utils.showToast(this, it.message.toString(), Toast.LENGTH_LONG)
                    }
                    Status.LOADING -> {


                    }

                }
            }
        }
    }

    private fun setupUI(response: Response?) {
        if(response!=null) {
            setBannerData(response)

            if(response.data!=null) {
                if (!response.data?.name.isNullOrEmpty())
                    tvMainTitle.text = response.data?.name


                if(!response.data?.average_rating.isNullOrEmpty()){
                    tvRate.text=response.data?.average_rating
                }

                if(!response.data?.currency_code.isNullOrEmpty()){
                    var currency=StringBuffer()
                    currency.append(response.data?.currency_code)
                    currency.append(" ")
                    currency.append(response.data?.final_price)
                    tvCurrency.text=currency.toString()

                    var stringBuffer=StringBuffer()
                    stringBuffer.append(response.data?.currency_code)
                    stringBuffer.append(" ")
                    stringBuffer.append(response.data?.regular_price)
                    tvPrice.text=stringBuffer.toString()

                    tvPrice.paintFlags = tvPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

                }
                if(!response.data?.SKU.isNullOrEmpty()){
                    tvSize.text= String.format(getString(R.string.txt_kwd), response.data?.SKU)
                }

                if(!response.data?.boutique_name.isNullOrEmpty()){
                    tvSizeType.text= response.data?.boutique_name
                }



            }
        }
    }
    private fun setBannerData(response: Response?){
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
        val txtSpannable = SpannableString(string1)
        val boldSpan = StyleSpan(Typeface.BOLD)
        txtSpannable.setSpan(boldSpan, 0, string1.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        builder.append(txtSpannable)
        builder.append(string2)
        textView.setText(builder, TextView.BufferType.SPANNABLE)
    }
}

