package com.test.dubizzleapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * This data class for handling API response
 */
@Parcelize
data class DataResponse(
    @SerializedName("results") val results: List<DataItem>,
    @SerializedName("pagination") val paginationData: PaginationData
) : Parcelable