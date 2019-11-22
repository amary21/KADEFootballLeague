package com.wapick.dosmu.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class DateConvert {
//
//    @SuppressLint("SimpleDateFormat")
//    fun convert(date: String): String {
//        var date = date
//        try {
//            var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
//            val convertDate = simpleDateFormat.parse(date)
//            simpleDateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.US)
//            date = simpleDateFormat.format(convertDate)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        return date
//    }

    @SuppressLint("SimpleDateFormat")
    fun convertMonth(time: String): String {
        var month = time
        try {
            var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val convertMonth = simpleDateFormat.parse(month)
            simpleDateFormat = SimpleDateFormat("MMM")
            month = simpleDateFormat.format(convertMonth)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return month
    }

    @SuppressLint("SimpleDateFormat")
    fun convertDay(time: String): String {
        var day = time
        try {
            var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val convertDay = simpleDateFormat.parse(day)
            simpleDateFormat = SimpleDateFormat("dd")
            day = simpleDateFormat.format(convertDay)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return day
    }
}