package com.example.nstustudentapp.enter.di

import android.content.SharedPreferences
import com.example.nstustudentapp.enter.data.datasource.GetStudentDataSourceImpl
import com.example.nstustudentapp.enter.data.repository.StudentRepositoryImpl
import com.example.nstustudentapp.enter.domain.GetStudentUseCase
import com.example.nstustudentapp.enter.presentation.ScreenSaverPresenter

object GetStudentPresenterFactory {

    fun create(mSettings: SharedPreferences): ScreenSaverPresenter {
        val studentDataSource = GetStudentDataSourceImpl()
        val studentRepository = StudentRepositoryImpl(studentDataSource)
        val getStudentUseCase = GetStudentUseCase(studentRepository)

        return ScreenSaverPresenter(mSettings, getStudentUseCase)
    }

}