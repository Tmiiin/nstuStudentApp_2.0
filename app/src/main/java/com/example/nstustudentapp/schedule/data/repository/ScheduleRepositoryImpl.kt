package com.example.nstustudentapp.schedule.data.repository

import com.example.nstustudentapp.enter.data.model.ScheduleResponseModel
import com.example.nstustudentapp.schedule.data.datasource.ScheduleDataSource
import com.example.nstustudentapp.schedule.domain.ScheduleRepository
import io.reactivex.Single

class ScheduleRepositoryImpl(private val dataSource: ScheduleDataSource) : ScheduleRepository {
    override fun getSchedule(token: String): Single<ScheduleResponseModel> =
        dataSource.getSchedule(token)

}