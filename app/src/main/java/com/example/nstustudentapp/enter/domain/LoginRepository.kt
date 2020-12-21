package com.example.nstustudentapp.enter.domain

import com.example.nstustudentapp.enter.data.model.AuthServerResponseModel
import io.reactivex.Single

interface LoginRepository {

    fun letAuthorize(login: String, password: String): Single<AuthServerResponseModel>
}