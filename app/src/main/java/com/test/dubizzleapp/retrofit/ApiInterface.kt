package com.test.dubizzleapp.retrofit

import com.test.dubizzleapp.model.DataResponse
import retrofit2.Call
import retrofit2.http.GET

/**
 * This interface to handle the GET API call
 */
interface ApiInterface {

    @GET("default/dynamodb-writer")
    fun getData(): Call<DataResponse>
}