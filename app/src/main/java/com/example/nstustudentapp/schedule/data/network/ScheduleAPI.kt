package com.example.nstustudentapp.schedule.data.network

import com.example.nstustudentapp.enter.data.model.ScheduleResponseModel
import io.reactivex.Single
import retrofit2.http.*

interface ScheduleAPI {

    @Headers("Content-Type: application/json")
    @GET("schedule")
    fun getSchedule(@Query("groupName") group: String): Single<ScheduleResponseModel>

   // @Header("Content-Type: application/json")
    @GET("schedule/getGroups")
    fun getListOfGroups(): Single<String>

    /**
     * there should be requests to get the schedule
     * */
}