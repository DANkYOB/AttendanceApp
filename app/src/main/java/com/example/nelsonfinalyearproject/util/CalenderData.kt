package com.example.nelsonfinalyearproject.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class CalenderData(
    var data: Date, var isSelected : Boolean = false
) {
    val calendarDay: String
        get() = SimpleDateFormat("EE", Locale.getDefault()).format(data)

    val calenderDate : String
        get() {
            val cal = Calendar.getInstance()
            cal.time = data
            return cal[Calendar.DAY_OF_MONTH].toString()
        }

}