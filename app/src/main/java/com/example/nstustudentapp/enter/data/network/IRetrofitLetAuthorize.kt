package com.example.nstustudentapp.enter.data.network

import com.example.nstustudentapp.Constants
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class IRetrofitLetAuthorize {

    private var gson = GsonBuilder()
        .setLenient()
        .create()

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.URLForAuthentification)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    private var service: LoginAPI = retrofit.create(LoginAPI::class.java)

    fun getRetrofitService(): LoginAPI {
        return this.service
    }
}