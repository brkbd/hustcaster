package com.hustcaster.app.utils

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.Locale
import kotlin.math.min

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

fun convertDurationStringToLong(durationString: String):Long{
    return when{
        durationString.contains(":")->{
            val timeParts=durationString.split(":")
            when(timeParts.size){
                3->{
                    val hours=timeParts[0].toLongOrNull()?:0
                    val minutes=timeParts[1].toLongOrNull()?:0
                    val seconds=timeParts[2].toLongOrNull()?:0
                    hours*3600+minutes*60+seconds
                }
                2->{
                    val minutes=timeParts[0].toLongOrNull()?:0
                    val seconds=timeParts[1].toLongOrNull()?:0
                    minutes*60+seconds
                }
                else->0
            }
        }
        durationString.all { it.isDigit() }->{
            durationString.toLongOrNull()?:0
        }
        else->0
    }
}

@SuppressLint("DefaultLocale")
fun convertLongToDurationString(totalSeconds:Long):String{
    val hours=totalSeconds/3600
    val remainingSeconds=totalSeconds%3600
    val minutes=remainingSeconds/60
    val seconds=remainingSeconds%60

    return String.format("%02d:%02d:%02d",hours, minutes,seconds)
}