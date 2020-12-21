package com.example.nstustudentapp.enter.data.repository

import com.example.nstustudentapp.enter.data.datasource.GetStudentDataSource
import com.example.nstustudentapp.enter.data.model.AuthServerResponseModel
import com.example.nstustudentapp.enter.domain.GetStudentRepository
import io.reactivex.Single

class StudentRepositoryImpl(private val dataSource: GetStudentDataSource) : GetStudentRepository {

    override fun getStudent(token: String): Single<AuthServerResponseModel> =
        dataSource.getStudent(token)

}