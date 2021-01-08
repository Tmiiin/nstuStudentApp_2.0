package com.example.nstustudentapp.schedule.presentation

import android.content.SharedPreferences
import android.util.Log
import com.example.nstustudentapp.Constants
import com.example.nstustudentapp.enter.data.model.AuthServerResponseModel
import com.example.nstustudentapp.enter.domain.LoginUseCase
import com.example.nstustudentapp.enter.ui.LoginFragment
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

    fun tryLogin(login: String, password: String) {
        val token = mSettings.getString(Constants.TOKEN, "nothing")
        if (token != "nothing") {
            try {
                myDisposable = token?.let {
                    getScheduleUseCase(it)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ success -> handleLoginResponse(success) },
                            { error -> doOnError(error) })
                }
            } catch (e: java.lang.Exception) {
                Log.e(TAG, e.message.toString())
               // view?.goToLogInFragment()
            }
        }
        else doOnError(Throwable("Token is empty"))
    }

    private fun doOnError(e: Throwable) {
        Log.e(TAG, e.message.toString())
        //view?.showLoginError()
    }

    private fun handleLoginResponse(msg: AuthServerResponseModel) {
        Log.i(TAG, "token is: ${msg.tokenId}")
        //    view?.setToken(msg.tokenId)
        //    view?.goToScheduleFragment()
    }

    fun onDestroy() {
        this.myDisposable?.dispose()
        this.myDisposable = null
        this.view = null
    }
}