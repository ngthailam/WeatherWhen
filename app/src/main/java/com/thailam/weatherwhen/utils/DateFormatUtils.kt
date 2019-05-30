package com.thailam.weatherwhen.utils

import android.content.Context
import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

object DateFormatUtils {
    private val TIME_FORMATTER = SimpleDateFormat("dd-MM-yyyy")
    private const val FORMAT_D_M_Y = "%d-%d-%d"

    /**
     * returns current date in "Month dd, yyyy" format
     */
    @JvmStatic
    fun getCurrentDate(): String =
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    /**
     * returns day of week from Long epoch value
     * @param context (Context) : context for android DateUtils
     * @param number (Long) : number in epoch date Long format
     */
    @JvmStatic
    fun longToDayOfWeek(context: Context, number: Long, abbreviate: Boolean = false): String =
        if (abbreviate) {
            DateUtils.formatDateTime(
                context, number * 1000, DateUtils.FORMAT_SHOW_WEEKDAY or DateUtils.FORMAT_ABBREV_WEEKDAY
            )
        } else {
            DateUtils.formatDateTime(context, number * 1000, DateUtils.FORMAT_SHOW_WEEKDAY)
        }

    /**
     * returns format (DayOfWeek Month dd, yyyy) from Long epoch value
     * @param context (Context) : context for android DateUtils
     * @param number (Long) : number in epoch date Long format
     */
    @JvmStatic
    fun longToDefaultDateTime(context: Context, number: Long): String =
        StringBuilder()
            .append(longToDayOfWeek(context, number))
            .append(" | ")
            .append(DateUtils.formatDateTime(context, number * 1000, 0))
            .toString()

    /**
     * returns long from year-month-dayOfMonth
     * @param year (Int) year : yyyy
     * @param month (Int) month : mm
     * @param dayOfMonth (Int) date : dd
     */
    fun dateToLong(year: Int, month: Int, dayOfMonth: Int): Long {
        val dateStr = String.format(Locale.getDefault(), FORMAT_D_M_Y, dayOfMonth, month, year)
        return TIME_FORMATTER.parse(dateStr).time
    }
}
