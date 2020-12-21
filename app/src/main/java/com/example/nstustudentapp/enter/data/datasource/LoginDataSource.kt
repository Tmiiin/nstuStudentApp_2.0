package com.example.nstustudentapp.enter.data.datasource

import com.example.nstustudentapp.enter.data.model.AuthServerResponseModel
import io.reactivex.Single

interface LoginDataSource {

    fun letAuthorize(login: String, password: String): Single<AuthServerResponseModel>

}