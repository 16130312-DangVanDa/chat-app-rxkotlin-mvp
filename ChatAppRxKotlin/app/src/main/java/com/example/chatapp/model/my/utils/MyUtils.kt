package com.example.chatapp.model.my.utils

import java.text.SimpleDateFormat
import java.util.*

class MyUtils {

    fun convertLongToTime(time: Long, format: String): String {
        val date = Date(time)
        return SimpleDateFormat(format).format(date)
    }
}