package com.amary.kade_footballeague.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class DateConvert {

    @SuppressLint("SimpleDateFormat")
    fun convertDate(dateEvent: String): String {
        var date = dateEvent
        try {
            var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            val convertDate = simpleDateFormat.parse(date)
            simpleDateFormat = SimpleDateFormat("dd MMM yyyy", Locale.US)
            date = simpleDateFormat.format(convertDate!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return date
    }

    @SuppressLint("SimpleDateFormat")
    fun convertTime(timeEvent: String): String {
        var time = timeEvent
        try {
            val simpleDateFormat = SimpleDateFormat("HH:mm:ss", Locale.US)
            simpleDateFormat.timeZone = TimeZone.getTimeZone("GMT")
            val convertTime = simpleDateFormat.parse(time)
            simpleDateFormat.timeZone = TimeZone.getDefault()
            time = simpleDateFormat.format(convertTime!!)

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return time
    }

}