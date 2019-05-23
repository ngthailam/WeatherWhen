package com.thailam.weatherwhen.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.thailam.weatherwhen.data.model.DailyForecast.Companion.TBL_FORECAST_NAME

@Entity(tableName = TBL_FORECAST_NAME)
data class DailyForecast(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @SerializedName(API_DATE) @ColumnInfo(name = TBL_FORECAST_DATE)
    val date: String,
    @SerializedName(API_EPOCH_DATE) @ColumnInfo(name = TBL_FORECAST_EPOCH)
    val epochDate: Int,
    @Embedded(prefix = TBL_FORECAST_PREFIX_DAY)
    @SerializedName(API_DAY)
    val day: CurrentCondition,
    @Embedded(prefix = TBL_FORECAST_PREFIX_NIGHT)
    @SerializedName(API_NIGHT)
    val night: CurrentCondition
) {
    companion object {
        // API responses serialized name
        const val API_DATE = "Date"
        const val API_EPOCH_DATE = "EpochDate"
        const val API_DAY = "Day"
        const val API_NIGHT = "Night"
        // Local database entries
        const val TBL_FORECAST_NAME = "daily_forecasts"
        const val TBL_FORECAST_DATE = "date"
        const val TBL_FORECAST_EPOCH = "epoch_date"
        const val TBL_FORECAST_PREFIX_DAY = "day_"
        const val TBL_FORECAST_PREFIX_NIGHT = "night_"

    }
}
