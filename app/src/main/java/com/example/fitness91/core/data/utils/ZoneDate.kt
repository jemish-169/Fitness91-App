package com.example.fitness91.core.data.utils

import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Calendar

fun getTodayDate(): String {
    return ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toLocalDate()
        .toString() // do not change this it is synced with DB
}

fun getTodayDay(): Int {
    val dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
    return if (dayOfWeek == Calendar.SUNDAY) 7 else dayOfWeek - 1
}