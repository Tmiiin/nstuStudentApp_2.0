package com.example.nstustudentapp.enter.data.network

import com.example.nstustudentapp.enter.data.model.AuthServerResponseModel
import io.reactivex.Single
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface GetStudentAPI {

    @Headers("Content-Type: application/json")
    @POST("student")
    fun getStudent(@Header("Token") token: String): Single<AuthServerResponseModel>

}