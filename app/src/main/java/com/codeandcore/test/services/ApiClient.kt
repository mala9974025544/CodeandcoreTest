package com.codeandcore.test.services


import android.content.Context
import android.util.Log
import android.widget.Toast
import com.codeandcore.test.BuildConfig
import com.codeandcore.test.ShoppingApp
import com.codeandcore.test.utils.Utils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.jvm.Throws


class ApiClient {


    private var retrofit: Retrofit? = null
    private  val REQUEST_TIMEOUT = 60
    private var okHttpClient: OkHttpClient? = null


     private fun getClient(): Retrofit? {

         if (!Utils.isNetworkAvailable(ShoppingApp.instance?.applicationContext) && ShoppingApp.instance?.applicationContext != null) {
            Utils.showToast(
                    ShoppingApp.instance?.applicationContext,
                "Please check your internet connection",
                Toast.LENGTH_SHORT
            )

        }


         if (okHttpClient == null)
             ShoppingApp.instance?.applicationContext?.let { initOkHttp(it) }

         if (retrofit == null) {
          retrofit = Retrofit.Builder()
                 .baseUrl(BuildConfig.BASE_URL)
                 .client(okHttpClient)
                 //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                 .addConverterFactory(GsonConverterFactory.create())
                 .build()
         }
         return retrofit

    }

    val apiService: ApiInterface = getClient()!!.create(ApiInterface::class.java)


    private fun initOkHttp(context: Context) {
        val httpClient = OkHttpClient().newBuilder()
            .connectTimeout(REQUEST_TIMEOUT.toLong(),TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT.toLong(),TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT.toLong(),TimeUnit.SECONDS)
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                val original = chain.request()
                val requestBuilder =
                    original.newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Request-Type", "Android")
                        .addHeader("Content-Type", "application/json")

                val request = requestBuilder!!.build()
                val response = chain.proceed(request)
                Log.d("request===",request.toString())
                if (response.code == 401) {
                    Utils.showToast(context, "Something went Wrong", Toast.LENGTH_SHORT);

                }
                return response
            }
        })
       okHttpClient = httpClient.build()
    }
}