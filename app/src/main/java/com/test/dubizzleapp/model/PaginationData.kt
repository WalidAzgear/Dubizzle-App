package com.test.dubizzleapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * This data class for handling pagination data from the API response.
 */
@Parcelize
data class PaginationData(@SerializedName("key") val key: String) : Parcelable