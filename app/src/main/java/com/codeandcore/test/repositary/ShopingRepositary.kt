package com.codeandcore.test.repositary

import com.codeandcore.test.services.ApiInterface


class ShopingRepositary(private val apiInterface: ApiInterface) {

    suspend fun getShoppingData(productId:String) = apiInterface.getShoppingData(productId)
}