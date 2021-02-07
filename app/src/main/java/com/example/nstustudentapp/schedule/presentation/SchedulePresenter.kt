package com.example.nstustudentapp.schedule.presentation

import android.content.SharedPreferences
import android.util.Log
import com.example.nstustudentapp.enter.data.model.ScheduleResponseModel
import com.example.nstustudentapp.schedule.domain.GetScheduleUseCase
import com.example.nstustudentapp.schedule.ui.ScheduleFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SchedulePresenter(
    private val getScheduleUseCase: GetScheduleUseCase,
    private val mSettings: SharedPreferences
) {

    private var view: ScheduleFragment? = null
    var myDisposable: Disposable? = null
    val TAG: String = "SchedulePresenter"

    fun attachView(view: ScheduleFragment) {
        this.view = view
    }

    private fun isValid(login: String, password: String): Boolean {
        if (login.length > 1 && password.length > 1) return true
        return false
    }

    fun getSchedule(group: String) {
        if(group.isNotEmpty()){
            try {
                myDisposable = group.let {
                    getScheduleUseCase(it)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ success -> handleLoginResponse(success) },
                            { error -> doOnError(error) })
                }
            } catch (e: java.lang.Exception) {
                Log.e(TAG, e.message.toString())
            }
        }
        else doOnError(Throwable("Group wasn't choosen"))
    }

    private fun doOnError(e: Throwable) {
        Log.e(TAG, e.message.toString())
        view?.showError("Не вышло получить расписание")
    }

    private fun handleLoginResponse(msg: ScheduleResponseModel) {
        Log.i(TAG, "token is: ${msg.responseBody}")
        view?.setMapOfLessons(msg.responseBody)
    }

    fun onDestroy() {
        this.myDisposable?.dispose()
        this.myDisposable = null
        this.view = null
    }
}