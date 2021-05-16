package com.example.nstustudentapp.schedule.data.network

import com.example.nstustudentapp.Constants
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body

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

    private var retrofitTeacher: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.ciu.nstu.ru/v1.1/student/get_data/app/")
        .addConverterFactory((GsonConverterFactory.create(gson)))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(getClient())
        .build()

    fun getClient(): OkHttpClient{
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build();
    }



    private var serviceTeacher: ScheduleAPI = retrofitTeacher.create(ScheduleAPI::class.java)

    private var service: ScheduleAPI = retrofit.create(ScheduleAPI::class.java)

    fun getRetrofitTeacherService(): ScheduleAPI{
        return this.serviceTeacher
    }

    fun getRetrofitService(): ScheduleAPI {
        return this.service
    }

}