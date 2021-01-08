package com.example.nstustudentapp.schedule.di

import android.content.SharedPreferences
import com.example.nstustudentapp.schedule.data.datasource.ScheduleDataSourceImpl
import com.example.nstustudentapp.schedule.data.repository.ScheduleRepositoryImpl
import com.example.nstustudentapp.schedule.domain.GetScheduleUseCase
import com.example.nstustudentapp.schedule.presentation.SchedulePresenter

object SchedulePresenterFactory {

    fun create(mSettings: SharedPreferences): SchedulePresenter {
        val scheduleDataSource = ScheduleDataSourceImpl()
        val scheduleRepository = ScheduleRepositoryImpl(scheduleDataSource)
        val scheduleUseCase = GetScheduleUseCase(scheduleRepository)

        return SchedulePresenter(scheduleUseCase, mSettings)
    }

}