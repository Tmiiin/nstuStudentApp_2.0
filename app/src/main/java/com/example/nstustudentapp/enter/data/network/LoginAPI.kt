package com.example.nstustudentapp.enter.data.network

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginAPI {

    @Headers("Content-Type: application/json")
    @POST("token/auth")
    fun letAuthorize(
        @Header("X-OpenAM-Username") login: String,
        @Header("X-OpenAM-Password") password: String
    ): Single<Response<ResponseBody>>

    @GET("/token/refresh")
    fun refreshToken(@Header("Cookie") cookie: String): Single<Response<ResponseBody>>

}