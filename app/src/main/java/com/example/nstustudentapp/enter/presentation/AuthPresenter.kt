package com.example.nstustudentapp.enter.presentation

import android.content.Context
import android.util.Log
import com.example.nstustudentapp.Constants
import com.example.nstustudentapp.enter.data.network.IRetrofitLetAuthorize
import com.example.nstustudentapp.enter.ui.LoginFragment
import com.example.nstustudentapp.schedule.data.network.IRetrofitSchedule
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Response


class AuthPresenter {

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
                    IRetrofitLetAuthorize().getRetrofitService().letAuthorize(login, password)
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

    fun isTokenValid(token: String) {
        myDisposable = IRetrofitSchedule().getRetrofitTeacherService().getTeacherInfo("access_token_cookie=" + token, 845)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { success ->
                onSuccess(success, token)
            }
    }

    fun onSuccess(success: Response<ResponseBody>, token: String) {
        if (success.body().toString().contains("access_token_cookie")) {
            val cookie = "${Companion.accessToken}=${token};"
            val q = IRetrofitLetAuthorize().getRetrofitService().refreshToken(cookie)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { success -> handleLoginResponse(success) },
                    { error -> view?.showLoginError() })
        } else {
            if(success.body() == null) {
                tryToRefreshToken()
            } else view?.goToScheduleFragment()
        }
    }

    private fun tryToRefreshToken() {
        val mSettings = view!!.requireContext()
            .getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE)!!
       val refreshToken = mSettings.getString(AuthPresenter.refreshToken, "")
        val cookie = "${Companion.refreshToken}=${refreshToken}"
        myDisposable = IRetrofitLetAuthorize().getRetrofitService().refreshToken(cookie)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ success -> handleLoginResponse(success) },
                { error -> doOnError(error) })
    }

    private fun doOnError(e: Throwable) {
        Log.e(TAG, e.message.toString())
        view?.showLoginError()
    }

    private fun handleLoginResponse(msg: Response<ResponseBody>) {
        Log.i(TAG, "token is: ${msg.headers()}")
        val cookieList: List<String> = msg.headers().values("Set-Cookie")
        if (cookieList.size >= 2) {
            val accessToken = cookieList[0].split(";")[0].split("=")[1]
            val refreshtoken = cookieList[1].split(";")[0].split("=")[1]
            view?.setToken(accessToken, Companion.accessToken)
            view?.setToken(refreshtoken, Companion.refreshToken)
            view?.goToScheduleFragment()
        } else doOnError(Throwable("Ошибка авторизации"))
    }

    fun onDestroy() {
        this.myDisposable?.dispose()
        this.myDisposable = null
        this.view = null
    }

    companion object {
        const val accessToken = "access_token_cookie"
        const val refreshToken = "refresh_token_cookie"
        const val csrnRefreshToken = "csrf_refresh_token"
    }

}