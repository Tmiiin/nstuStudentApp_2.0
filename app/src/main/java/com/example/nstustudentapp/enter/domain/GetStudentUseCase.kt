package com.example.nstustudentapp.enter.domain

import com.example.nstustudentapp.enter.data.model.AuthServerResponseModel
import io.reactivex.Single

class GetStudentUseCase(private val loginRepository: GetStudentRepository) {

    operator fun invoke(token: String): Single<AuthServerResponseModel> {
        return loginRepository.getStudent(token)
    }

}