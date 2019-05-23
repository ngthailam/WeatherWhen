package com.thailam.weatherwhen.di

import com.thailam.weatherwhen.data.local.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val forecastModule = module(override = true) {
    single { AppDatabase.getInstance(androidContext()) }
    factory { get<AppDatabase>().dailyForecastDao() }
}
