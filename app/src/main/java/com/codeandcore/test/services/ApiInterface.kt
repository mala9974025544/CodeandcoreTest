package com.codeandcore.test.services

import com.codeandcore.test.datamodels.AtttibuteResponseList
import com.codeandcore.test.datamodels.ConfigurableOption
import com.codeandcore.test.datamodels.Request
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*


interface ApiInterface {

    @GET("product-details")
    suspend fun getShoppingData(
        @Query("product_id") product_id: String?
        ): Response<com.codeandcore.test.datamodels.Response>


    @POST("configurable-options")
    suspend fun addToBag(
        @Body request:Request
    ): Response<AtttibuteResponseList>


}