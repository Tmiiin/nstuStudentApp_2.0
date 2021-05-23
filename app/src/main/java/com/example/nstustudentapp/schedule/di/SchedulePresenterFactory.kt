package com.example.nstustudentapp.schedule.di

import android.content.SharedPreferences
import android.util.Log
import com.example.nstustudentapp.schedule.data.datasource.ScheduleDataSourceImpl
import com.example.nstustudentapp.schedule.data.repository.ScheduleRepositoryImpl
import com.example.nstustudentapp.schedule.domain.GetScheduleUseCase
import com.example.nstustudentapp.schedule.presentation.ScheduleViewModel

object SchedulePresenterFactory {

    val TAG: String = "ScheduleFactory"

    fun create(): ScheduleViewModel {
        try {
            val scheduleDataSource = ScheduleDataSourceImpl()
            val scheduleRepository = ScheduleRepositoryImpl(scheduleDataSource)
            val scheduleUseCase = GetScheduleUseCase(scheduleRepository)

        return ScheduleViewModel(scheduleUseCase)

        }catch(e:Exception){
            Log.e(TAG, e.message.toString())
        }
        throw java.lang.Exception("ldl;ld;e")
    }
    }
