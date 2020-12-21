package com.example.nstustudentapp.enter.domain

import com.example.nstustudentapp.enter.data.model.AuthServerResponseModel
import io.reactivex.Single

class LoginUseCase(private val loginRepository: LoginRepository) {

    operator fun invoke(login: String, password: String): Single<AuthServerResponseModel> {
        return loginRepository.letAuthorize(login, password)
    }
}