package com.example.nstustudentapp.schedule.presentation

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DateUtils {

    private lateinit var calendar: Calendar

    fun getCurrentWeek(): MutableList<String> {
        calendar = Calendar.getInstance()
        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        return getNextWeek()
    }

    fun getNextWeek(): MutableList<String> {
        val format: DateFormat = SimpleDateFormat("dd.MM")
        val days = mutableListOf<String>()
        for (i in 0..6) {
            days.add(format.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return days
    }

    fun getPreviousWeek(): MutableList<String> {
        calendar.add(Calendar.DATE, -14)
        return getNextWeek()
    }

}