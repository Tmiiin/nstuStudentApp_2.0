package com.example.nstustudentapp.enter.data.datasource

import android.util.Log
import com.example.nstustudentapp.enter.data.model.AuthServerResponseModel
import com.example.nstustudentapp.enter.data.network.IRetrofitLetAuthorize
import io.reactivex.Single


class LoginDataSourceImpl : LoginDataSource {

    companion object {
        val service = IRetrofitLetAuthorize().getRetrofitService()
    }
    val TAG = "LoginDataSource"

    override fun letAuthorize(login: String, password: String): Single<AuthServerResponseModel> =
        try {
            Log.i(TAG, "handle authorize...")
            service.letAuthorize(login, password)
        } catch (e: Exception) {
            throw Exception(e.message)
        }

}