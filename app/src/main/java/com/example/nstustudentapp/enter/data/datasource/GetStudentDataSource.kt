package com.example.nstustudentapp.enter.data.datasource

import com.example.nstustudentapp.enter.data.model.AuthServerResponseModel
import io.reactivex.Single

interface GetStudentDataSource {

    fun getStudent(token: String): Single<AuthServerResponseModel>

}