package com.example.nstustudentapp.schedule.data.network

import com.example.nstustudentapp.enter.data.model.AuthServerResponseModel
import io.reactivex.Single
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ScheduleAPI {
    //wrong query model!
    @Headers("Content-Type: application/json")
    @POST("student")
    fun getSchedule(@Header("Token") token: String): Single<AuthServerResponseModel>

    /**
     * there should be requests to get the schedule
     * */
}