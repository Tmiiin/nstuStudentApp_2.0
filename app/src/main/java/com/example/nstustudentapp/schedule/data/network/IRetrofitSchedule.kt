package com.example.nstustudentapp.schedule.data.network

import com.example.nstustudentapp.Constants
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class IRetrofitSchedule {

    private var gson = GsonBuilder()
        .setLenient()
        .create()

        private var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(Constants.URLforGetSchedule)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    private var service: ScheduleAPI = retrofit.create(ScheduleAPI::class.java)

    fun getRetrofitService(): ScheduleAPI {
        return this.service
    }

}