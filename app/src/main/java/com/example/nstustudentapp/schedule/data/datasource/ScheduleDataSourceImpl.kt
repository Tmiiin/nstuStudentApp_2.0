package com.example.nstustudentapp.schedule.data.datasource

import android.util.Log
import com.example.nstustudentapp.enter.data.model.AuthServerResponseModel
import com.example.nstustudentapp.schedule.data.network.IRetrofitSchedule
import io.reactivex.Single

class ScheduleDataSourceImpl : ScheduleDataSource {
    companion object {
        val service = IRetrofitSchedule().getRetrofitService()
    }

    val TAG = "ScheduleDataSource"

    override fun getSchedule(token: String): Single<AuthServerResponseModel> =
        try {
            Log.i(TAG, token)
            service.getSchedule(token)
        } catch (e: Exception) {
            throw Exception(e.message)
        }

}