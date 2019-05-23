package com.thailam.weatherwhen.data.repository

import com.thailam.weatherwhen.data.ForecastDataSource
import com.thailam.weatherwhen.data.model.DailyForecast
import com.thailam.weatherwhen.data.model.LocationResponse
import com.thailam.weatherwhen.data.model.WeatherResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class ForecastRepositoryImpl(
    val forecastRemoteDataSource: ForecastDataSource.Remote,
    val forecastLocalDataSource: ForecastDataSource.Local
) : ForecastRepository {
    /**
     * main method to be used by view model
     */
    override fun fetchDailyForecasts(geoposition: Pair<String, String>): Single<List<DailyForecast>> {
        val shouldRefresh = true
        return if (shouldRefresh) {
            getLocationKey(geoposition.toLocationKey())
                .subscribeOn(Schedulers.io())
                .flatMap {
                    getDailyForecastsRemote(it.key)
                }
                .map {
                    it.dailyForecasts
                }
        } else { // no need to refresh/ no internet -> default to local database
            getDailyForecastsLocal()
        }
    }

    override fun addDailyForecasts(dailyForecasts: List<DailyForecast>) =
        forecastLocalDataSource.addDailyForecasts(dailyForecasts)

    override fun getDailyForecastsLocal(): Single<List<DailyForecast>> =
        forecastLocalDataSource.getDailyForecastsLocal()

    override fun getLocationKey(geoposition: String): Single<LocationResponse> =
        forecastRemoteDataSource.getLocationKey(geoposition)

    override fun getDailyForecastsRemote(locationKey: String): Single<WeatherResponse> =
        forecastRemoteDataSource.getDailyForecastsRemote(locationKey)

    // extension function to convert lat long pair to string compatible with api request
    fun Pair<String, String>.toLocationKey(): String =
        StringBuilder().apply {
            append(this@toLocationKey.first)
            append(",")
            append(this@toLocationKey.second)
        }.toString()
}

interface ForecastRepository : ForecastDataSource.Remote, ForecastDataSource.Local {
    fun fetchDailyForecasts(geoposition: Pair<String, String>): Single<List<DailyForecast>>
}
