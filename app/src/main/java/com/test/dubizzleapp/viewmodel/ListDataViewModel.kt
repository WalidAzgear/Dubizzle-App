package com.test.dubizzleapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.dubizzleapp.model.DataResponse
import com.test.dubizzleapp.repository.ListDataRepository
import com.test.dubizzleapp.repository.Resource

/**
 * This view model class responsable to get the data from repository and observe it in the activity to show the data.
 */
class ListDataViewModel : ViewModel() {

    var itemsLiveData: MutableLiveData<Resource<DataResponse>>? = null

    fun getData(): LiveData<Resource<DataResponse>>? {
        itemsLiveData = ListDataRepository.getDataApiCall()
        return itemsLiveData
    }
}