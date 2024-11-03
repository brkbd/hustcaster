package com.hustcaster.app.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.Locale

class Converters {
    @TypeConverter
    fun calendarToDatestamp(calendar: Calendar): Long = calendar.timeInMillis

    @TypeConverter
    fun datestampToCalendar(value: Long): Calendar =
        Calendar.getInstance().apply { timeInMillis = value }
}

@RequiresApi(Build.VERSION_CODES.O)
fun convertStringToCalendar(dateString: String): Calendar? {
    val formats = arrayOf(
        DateTimeFormatter.ofPattern(
            "EEE, dd MMM yyyy HH:mm:ss Z",
            Locale.ENGLISH
        ), // "Tue, 29 Oct 2024 20:00:00 +0000"
        DateTimeFormatter.ofPattern(
            "EEE, dd MMM yyyy HH:mm:ss 'GMT'",
            Locale.ENGLISH
        ) // "Tue, 29 Oct 2024 20:00:00 GMT"
    )
    for (format in formats) {
        try {
            val zonedDateTime = ZonedDateTime.parse(dateString, format)
            val calendar = GregorianCalendar.from(zonedDateTime)
            return calendar
        } catch (e: DateTimeParseException) {
            e.printStackTrace()
        }
    }
    return null
}