package com.example.nstustudentapp.enter.data.repository

import com.example.nstustudentapp.enter.data.datasource.LoginDataSource
import com.example.nstustudentapp.enter.data.model.AuthServerResponseModel
import com.example.nstustudentapp.enter.domain.LoginRepository
import io.reactivex.Single

class LoginRepositoryImpl(private val dataSource: LoginDataSource) : LoginRepository {

    override fun letAuthorize(login: String, password: String): Single<AuthServerResponseModel> =
        dataSource.letAuthorize(login, password)

}