package com.thailam.weatherwhen.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.thailam.weatherwhen.data.local.dao.DailyForecastDao
import com.thailam.weatherwhen.data.model.DailyForecast


@Database(entities = [DailyForecast::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dailyForecastDao(): DailyForecastDao

    companion object {
        private const val DB_NAME = "weatherWhen.db"
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DB_NAME
            ).build()
    }
}
