package com.grv.common.util

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

fun Date.formatDateAsYearMonthDay(): String {
    val localDate = this.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    return localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
}

fun String.formatCardDate(): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val dateTime = LocalDateTime.parse(this, inputFormatter)

    val outputFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy h:mm a")
    return dateTime.format(outputFormatter)
}
