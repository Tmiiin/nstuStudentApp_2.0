package com.example.nstustudentapp.enter.di

import android.content.SharedPreferences
import com.example.nstustudentapp.enter.data.datasource.GetStudentDataSourceImpl
import com.example.nstustudentapp.enter.data.datasource.LoginDataSourceImpl
import com.example.nstustudentapp.enter.data.repository.LoginRepositoryImpl
import com.example.nstustudentapp.enter.data.repository.StudentRepositoryImpl
import com.example.nstustudentapp.enter.domain.GetStusentUseCase
import com.example.nstustudentapp.enter.presentation.ScreenSaverPresenter

object GetStudentPresenterFactory {

    fun create(mSettings: SharedPreferences): ScreenSaverPresenter {
        val studentDataSource = GetStudentDataSourceImpl()
        val studentRepository = StudentRepositoryImpl(studentDataSource)
        val getStudentUseCase = GetStusentUseCase(studentRepository)

        return ScreenSaverPresenter(mSettings, getStudentUseCase)
    }

}