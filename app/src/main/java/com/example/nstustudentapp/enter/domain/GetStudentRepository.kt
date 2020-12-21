package com.example.nstustudentapp.enter.domain

import com.example.nstustudentapp.enter.data.model.AuthServerResponseModel
import io.reactivex.Single

interface GetStudentRepository {

    fun getStudent(token: String): Single<AuthServerResponseModel>

}