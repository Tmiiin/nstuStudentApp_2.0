package com.example.nstustudentapp.schedule.presentation

import androidx.lifecycle.MutableLiveData
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DateUtils {

    private lateinit var calendar: Calendar
    var dateLiveData = MutableLiveData<List<String>>()

    fun getCurrentWeek() {
        calendar = Calendar.getInstance()
        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        getNextWeek()
    }

    fun getNextWeek() {
        val format: DateFormat = SimpleDateFormat("dd.MM")
        val days = mutableListOf<String>()
        for (i in 0..6) {
            days.add(format.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        dateLiveData.postValue(days)
    }

    fun getPreviousWeek() {
        calendar.add(Calendar.DATE, -14)
        getNextWeek()
    }

}