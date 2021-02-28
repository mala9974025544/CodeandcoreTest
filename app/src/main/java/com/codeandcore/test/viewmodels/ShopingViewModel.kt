package com.codeandcore.test.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.codeandcore.test.datamodels.ConfigurableOption
import com.codeandcore.test.datamodels.Request
import com.codeandcore.test.repositary.ShopingRepositary
import com.codeandcore.test.services.ApiInterface
import com.stylist.services.Resource
import kotlinx.coroutines.Dispatchers


class ShopingViewModel(private val shopingRepositary: ShopingRepositary):  ViewModel() {

    fun getHoliday(productId:String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = shopingRepositary.getShoppingData(productId)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun addTobeg(request: Request) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = shopingRepositary.addtoBag(request)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
    class ViewModelFactory(private val apiInterface: ApiInterface) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ShopingViewModel::class.java)) {
                return ShopingViewModel(ShopingRepositary(apiInterface)) as T
            }
            throw IllegalArgumentException("Unknown class name")
        }

    }
}