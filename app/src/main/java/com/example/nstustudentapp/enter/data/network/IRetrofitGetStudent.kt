package com.example.nstustudentapp.enter.data.network

import com.example.nstustudentapp.Constants
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


class IRetrofitGetStudent {

    private var gson = GsonBuilder()
        .setLenient()
        .create()

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.URLforGetStudent)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    private var service: GetStudentAPI = retrofit.create(GetStudentAPI::class.java)

    fun getRetrofitService(): GetStudentAPI {
        return this.service
    }
}