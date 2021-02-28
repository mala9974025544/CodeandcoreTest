package com.codeandcore.test.repositary

import com.codeandcore.test.datamodels.ConfigurableOption
import com.codeandcore.test.datamodels.Request
import com.codeandcore.test.services.ApiInterface


class ShopingRepositary(private val apiInterface: ApiInterface) {

    suspend fun getShoppingData(productId:String) = apiInterface.getShoppingData(productId)
    suspend fun addtoBag(request: Request) = apiInterface.addToBag(request)
}