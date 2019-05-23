package com.thailam.weatherwhen.data.repository

import com.thailam.weatherwhen.data.model.DailyForecast
import io.reactivex.Single

interface ForecastRepository {
    fun get5DaysForecast(): Single<List<DailyForecast>>
    fun addDailyForecasts(dailyForecasts: List<DailyForecast>)
}
