package com.codeandcore.test.ui

import android.os.Bundle
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
import kotlinx.android.synthetic.main.dash_banner.*

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
}

