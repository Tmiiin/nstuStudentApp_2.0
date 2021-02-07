package com.example.nstustudentapp.enter.data.model

import com.example.nstustudentapp.schedule.data.model.LessonsOnDay

data class ScheduleResponseModel(
    val status: String, val responseBody: ArrayList<LessonsOnDay>
){
}