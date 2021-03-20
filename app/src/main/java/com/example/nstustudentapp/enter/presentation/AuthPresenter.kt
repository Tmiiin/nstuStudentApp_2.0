package com.example.nstustudentapp.enter.presentation

import android.util.Log
import com.example.nstustudentapp.enter.data.model.AuthServerResponseModel
import com.example.nstustudentapp.enter.domain.LoginUseCase
import com.example.nstustudentapp.enter.ui.LoginFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class AuthPresenter(private val loginUseCase: LoginUseCase) {

    private var view: LoginFragment? = null
    var myDisposable: Disposable? = null
    val TAG: String = "Login"

    fun attachView(view: LoginFragment) {
        this.view = view
    }

    private fun isValid(login: String, password: String): Boolean {
        if (login.length > 1 && password.length > 1) return true
        return false
    }

    fun tryLogin(login: String, password: String) {
        if (isValid(login, password)) {
            try {
                myDisposable =
                    loginUseCase(login, password)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ success -> handleLoginResponse(success) },
                            { error -> doOnError(error) })
            } catch (e: java.lang.Exception) {
                Log.e(TAG, e.message.toString())
                view?.showLoginError()
            }
        }
    }

    private fun doOnError(e: Throwable) {
        Log.e(TAG, e.message.toString())
        view?.showLoginError()
    }

    private fun handleLoginResponse(msg: AuthServerResponseModel) {
        Log.i(TAG, "token is: ${msg.tokenId}")
        Log.i(TAG, msg.successUrl)
        view?.setToken(msg.tokenId)
        view?.goToScheduleFragment()
    }

    fun onDestroy() {
        this.myDisposable?.dispose()
        this.myDisposable = null
        this.view = null
    }

}