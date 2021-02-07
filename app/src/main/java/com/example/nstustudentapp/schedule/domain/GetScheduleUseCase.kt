package com.example.nstustudentapp.schedule.domain

import com.example.nstustudentapp.enter.data.model.ScheduleResponseModel
import io.reactivex.Single

class GetScheduleUseCase(private val scheduleRepository: ScheduleRepository)  {
    operator fun invoke(token: String): Single<ScheduleResponseModel> {
        return scheduleRepository.getSchedule(token)
    }
}