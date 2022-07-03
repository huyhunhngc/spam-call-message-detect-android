package com.dotsdev.idcaller.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun Long.convertTimestampToHours(): String {
    val calendarMess = Calendar.getInstance()
    val calendarNow = Calendar.getInstance()
    calendarMess.timeInMillis = this
    calendarNow.timeInMillis = System.currentTimeMillis()
    var dateFormat = SimpleDateFormat("hh:mm")
    val anteOrPost = if (calendarMess.get(Calendar.AM_PM) == Calendar.AM) {
        "AM"
    } else {
        "PM"
    }
    if (calendarMess.get(Calendar.YEAR) != calendarNow.get(Calendar.YEAR)) {
        dateFormat = SimpleDateFormat("dd MMM yyyy")
        return dateFormat.format(calendarMess.time)
    }
    if (calendarMess.get(Calendar.MONTH) != calendarNow.get(Calendar.MONTH)) {
        dateFormat = SimpleDateFormat("dd MMM")
        return dateFormat.format(calendarMess.time)
    }
    if (calendarMess.get(Calendar.WEEK_OF_MONTH) != calendarNow.get(Calendar.WEEK_OF_MONTH)) {
        dateFormat = SimpleDateFormat("EEE dd")
        return dateFormat.format(calendarMess.time)
    }
    if (calendarMess.get(Calendar.DAY_OF_MONTH) != calendarNow.get(Calendar.DAY_OF_MONTH)) {
        dateFormat = SimpleDateFormat("EEE")
        return dateFormat.format(calendarMess.time)
    }
    return "${dateFormat.format(calendarMess.time)} $anteOrPost"
}

fun Date.toTimeAndDatePattern(): String {
    val pattern = "MMM dd HH:mm"
    val simpleDateFormat = SimpleDateFormat(pattern)

    return simpleDateFormat.format(this)
}
