package com.example.nstustudentapp.schedule.domain

import com.example.nstustudentapp.enter.data.model.AuthServerResponseModel
import io.reactivex.Single

interface ScheduleRepository {
    fun getSchedule(token: String): Single<AuthServerResponseModel>
}