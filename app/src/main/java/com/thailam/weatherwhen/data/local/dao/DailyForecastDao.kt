package com.thailam.weatherwhen.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thailam.weatherwhen.data.model.DailyForecast
import com.thailam.weatherwhen.data.model.DailyForecast.Companion.TBL_FORECAST_NAME

@Dao
abstract class DailyForecastDao {
    @Query(value = "SELECT * FROM $TBL_FORECAST_NAME")
    abstract fun getDailyForecasts(): LiveData<List<DailyForecast>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun addDailyForecasts(dailyForecasts: List<DailyForecast>)
}
