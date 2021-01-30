package com.test.dubizzleapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * This data class for handling each item data from API response.
 */
@Parcelize
data class DataItem(
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("price") val price: String,
    @SerializedName("name") val name: String,
    @SerializedName("uid") val uid: String,
    @SerializedName("image_ids") val imageIds: List<String>,
    @SerializedName("image_urls") val imageUrls: List<String>,
    @SerializedName("image_urls_thumbnails") val imageUrlsThumbnails: List<String>
) : Parcelable