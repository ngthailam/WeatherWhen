package com.thailam.weatherwhen.data.repository

import com.thailam.weatherwhen.data.ForecastDataSource
import com.thailam.weatherwhen.data.model.DailyForecast
import io.reactivex.Single
import android.location.LocationManager
import android.content.Context.LOCATION_SERVICE
import androidx.core.content.ContextCompat.getSystemService



class ForecastRepositoryImpl(
    val forecastRemoteDataSource: ForecastDataSource.Remote,
    val forecastLocalDataSource: ForecastDataSource.Local
) : ForecastRepository {
    override fun get5DaysForecast(): Single<List<DailyForecast>> {
        // TODO: implement in later tasks
        return getForecastFromLocal()
    }

    override fun addDailyForecasts(dailyForecasts: List<DailyForecast>) =
        forecastLocalDataSource.addDailyForecasts(dailyForecasts)

    private fun getForecastFromLocal() =
        forecastLocalDataSource.getDailyForecasts()

    private fun getForecastFromRemote() {
        //
    }

    private fun getCurrentPosition() {
//        val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager?
//        val location = lm!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
//        val longitude = location.longitude
//        val latitude = location.latitude
    }
}
