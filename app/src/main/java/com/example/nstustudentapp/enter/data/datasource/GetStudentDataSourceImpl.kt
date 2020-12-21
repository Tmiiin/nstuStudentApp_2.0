package com.example.nstustudentapp.enter.data.datasource

import android.util.Log
import com.example.nstustudentapp.enter.data.model.AuthServerResponseModel
import com.example.nstustudentapp.enter.data.network.IRetrofitGetStudent
import io.reactivex.Single

class GetStudentDataSourceImpl: GetStudentDataSource {

    companion object {
        val service = IRetrofitGetStudent().getRetrofitService()
    }
    val TAG = "GetStudentDataSource"

    override fun getStudent(token: String): Single<AuthServerResponseModel> =
        try {
            Log.i(TAG, token)
            service.getStudent(token)
        } catch (e: Exception) {
            throw Exception(e.message)
        }


}