package com.example.nstustudentapp.enter.di

import com.example.nstustudentapp.enter.data.datasource.LoginDataSourceImpl
import com.example.nstustudentapp.enter.data.repository.LoginRepositoryImpl
import com.example.nstustudentapp.enter.domain.LoginUseCase
import com.example.nstustudentapp.enter.presentation.AuthPresenter

object LoginPresenterFactory {

    fun create(): AuthPresenter {
        val loginDataSource = LoginDataSourceImpl()
        val loginRepository = LoginRepositoryImpl(loginDataSource)
        val loginUseCase = LoginUseCase(loginRepository)

        return AuthPresenter(loginUseCase)
    }
}