package com.thailam.weatherwhen.data.model

import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @SerializedName(API_LOCATION_KEY) val key: String,
    @SerializedName(API_TYPE) val type: String,
    @SerializedName(API_ENGLISH_NAME) val englishName: String
) {
    companion object {
        const val API_LOCATION_KEY = "Key"
        const val API_TYPE = "Type"
        const val API_ENGLISH_NAME = "EnglishName"
    }
}
