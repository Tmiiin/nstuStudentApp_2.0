package com.example.nstustudentapp.enter.data.network

import com.example.nstustudentapp.enter.data.model.AuthServerResponseModel
import io.reactivex.Single
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginAPI {

    @Headers("Content-Type: application/json")
    @POST("authenticate")
    fun letAuthorize(
        @Header("X-OpenAM-Username") login: String,
        @Header("X-OpenAM-Password") password: String
    ): Single<AuthServerResponseModel>
}