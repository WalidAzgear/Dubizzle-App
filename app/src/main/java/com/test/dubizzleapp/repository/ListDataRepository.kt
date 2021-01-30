package com.test.dubizzleapp.repository

import androidx.lifecycle.MutableLiveData
import com.test.dubizzleapp.model.DataResponse
import com.test.dubizzleapp.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * This object responsable for API call and get the data.
 * This object contains MutableLiveData to post the response to the viewModel that called this API.
 */
object ListDataRepository {

    val dataListMutableLiveData = MutableLiveData<Resource<DataResponse>>()

    fun getDataApiCall(): MutableLiveData<Resource<DataResponse>> {
        val call = RetrofitClient.apiInterface.getData()
        call.enqueue(object : Callback<DataResponse> {

            override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                dataListMutableLiveData.value = Resource.error(t.message.toString(), null)
            }

            override fun onResponse(
                call: Call<DataResponse>, response: Response<DataResponse>
            ) {
                val body = response.body()
                val dataList = body!!.results
                val pagination = body.paginationData
                dataListMutableLiveData.value = Resource.success(DataResponse(dataList, pagination))
            }
        })
        return dataListMutableLiveData
    }
}