package com.codeandcore.test.ui

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.codeandcore.test.R
import com.codeandcore.test.datamodels.Response
import com.codeandcore.test.services.ApiClient
import com.codeandcore.test.utils.Utils
import com.codeandcore.test.viewmodels.ShopingViewModel
import com.stylist.services.Status

class MainActivity : BaseActivity() {

    private lateinit var shopingViewModel : ShopingViewModel
    var response= Response()
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

        var productId="124"
        var lang="en"
        var userId="46"
        var store="KW"
        shopingViewModel.getHoliday(productId = productId).observe(this) {
            it.let { resource ->
                when (resource.status) {
                    Status.SUCESS -> {

                        if (resource.data?.isSuccessful!!) {
                            resource.data.let { it1 ->

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

}