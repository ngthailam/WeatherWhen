package com.thailam.weatherwhen.data.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName(API_DAILYFORECASTS) val dailyForecasts: List<DailyForecast>
) {
    companion object {
        const val API_DAILYFORECASTS = "DailyForecasts"
    }
}
