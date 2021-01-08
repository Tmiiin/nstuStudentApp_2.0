package com.example.nstustudentapp.schedule.domain

import com.example.nstustudentapp.enter.data.model.AuthServerResponseModel
import io.reactivex.Single

class GetScheduleUseCase(private val scheduleRepository: ScheduleRepository)  {
    operator fun invoke(token: String): Single<AuthServerResponseModel> {
        return scheduleRepository.getSchedule(token)
    }
}