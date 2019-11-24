package com.amary.kade_footballeague.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class DateConvert {

    @SuppressLint("SimpleDateFormat")
    fun convert(dateEvent: String): String {
        var date = dateEvent
        try {
            var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            val convertDate = simpleDateFormat.parse(date)
            simpleDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.US)
            date = simpleDateFormat.format(convertDate)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return date
    }

}