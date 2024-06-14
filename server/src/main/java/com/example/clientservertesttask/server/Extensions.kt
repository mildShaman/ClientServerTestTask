package com.example.clientservertesttask.server

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toDateString(): String {
    val date = Date(this)
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())
    return dateFormat.format(date)
}