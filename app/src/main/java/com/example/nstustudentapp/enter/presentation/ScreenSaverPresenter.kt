package com.example.nstustudentapp.enter.presentation

import android.content.SharedPreferences
import android.util.Log
import com.example.nstustudentapp.Constants
import com.example.nstustudentapp.enter.data.model.AuthServerResponseModel
import com.example.nstustudentapp.enter.domain.GetStudentUseCase
import com.example.nstustudentapp.enter.ui.ScreenSaverFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ScreenSaverPresenter(
    private val mSettings: SharedPreferences,
    private val getStudentUseCase: GetStudentUseCase
) {

    private var view: ScreenSaverFragment? = null
    var myDisposable: Disposable? = null
    val TAG: String = "ScreenSaver"

    fun attachView(view: ScreenSaverFragment) {
        this.view = view
    }

    fun tryLogin() {
        val token = mSettings.getString(Constants.TOKEN, "nothing")
        if (token != "nothing") {
            try {
                myDisposable = token?.let {
                    getStudentUseCase(it)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ success -> handleLoginResponse(success) },
                            { error -> doOnError(error) })
                }
            } catch (e: java.lang.Exception) {
                Log.e(TAG, e.message.toString())
                view?.goToLogInFragment()
            }
        }
        else doOnError(Throwable("Token is empty"))
    }

    private fun doOnError(e: Throwable) {
        Log.e(TAG, e.message.toString())
        view?.goToLogInFragment()
    }

    private fun handleLoginResponse(msg: AuthServerResponseModel) {
        Log.i(TAG, "token is: ${msg.tokenId}")
        view?.goToScheduleFragment()
    }

    fun onDestroy() {
        this.myDisposable?.dispose()
        this.myDisposable = null
        this.view = null
    }

}