package com.test.dubizzleapp.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * This object to create retrofit client to use it when call the API service.
 */
object RetrofitClient {

    private const val baseURL = "https://ey3f2y0nre.execute-api.us-east-1.amazonaws.com/"
    private val retrofitClient: Retrofit.Builder by lazy {
        val okHttpClient = OkHttpClient.Builder()
        Retrofit.Builder()
            .baseUrl(baseURL)
            .client(okHttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiInterface: ApiInterface by lazy {
        retrofitClient
            .build()
            .create(ApiInterface::class.java)
    }
}