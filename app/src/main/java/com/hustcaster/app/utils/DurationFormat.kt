package com.hustcaster.app.utils

import android.annotation.SuppressLint

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