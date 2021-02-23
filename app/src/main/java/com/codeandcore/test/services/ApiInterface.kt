package com.codeandcore.test.services

import retrofit2.Response
import retrofit2.http.*


interface ApiInterface {

    @GET("product-details")
    suspend fun getShoppingData(
        @Query("product_id") product_id: String?
        ): Response<com.codeandcore.test.datamodels.Response>


}