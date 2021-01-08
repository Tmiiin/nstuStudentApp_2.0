package com.example.nstustudentapp.schedule.data.datasource

import com.example.nstustudentapp.enter.data.model.AuthServerResponseModel
import io.reactivex.Single

interface ScheduleDataSource {
    fun getSchedule(token: String): Single<AuthServerResponseModel>

}