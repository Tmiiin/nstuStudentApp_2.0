package com.example.nstustudentapp.enter.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nstustudentapp.enter.data.model.TokenInfo
import com.example.nstustudentapp.enter.data.network.IRetrofitLetAuthorize
import com.example.nstustudentapp.schedule.data.network.IRetrofitSchedule
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Response

class AuthViewModel : ViewModel() {

    companion object {
        const val TAG: String = "LoginViewModel"
        const val accessToken = "access_token_cookie"
        const val refreshToken = "refresh_token_cookie"
        val errorLiveData = MutableLiveData<Boolean>()
        val successAuthLiveData = MutableLiveData<Boolean>()
        val tokenLiveData = MutableLiveData<TokenInfo>()
    }

    private lateinit var refreshToken: String

    fun setRefreshToken(token: String){
        refreshToken = token
    }

    fun getTokenLiveData(): MutableLiveData<TokenInfo> {
        return tokenLiveData
    }

    fun getErrorLiveData(): MutableLiveData<Boolean> {
        return errorLiveData
    }

    fun getSuccessAuthLiveData(): MutableLiveData<Boolean> {
        return successAuthLiveData
    }

    private fun isValid(login: String, password: String): Boolean {
        if (login.length > 1 && password.length > 1) return true
        return false
    }

    @SuppressLint("CheckResult")
    fun tryLogin(login: String, password: String) {
        if (isValid(login, password)) {
            try {
                    IRetrofitLetAuthorize().getRetrofitService().letAuthorize(login, password)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ success -> handleLoginResponse(success) },
                            { errorLiveData.postValue(true) })
            } catch (e: java.lang.Exception) {
                Log.e(TAG, e.message.toString())
                errorLiveData.postValue(true)
            }
        }
    }

    @SuppressLint("CheckResult")
    fun isTokenValid(token: String) {
        IRetrofitSchedule().getRetrofitTeacherService()
            .getTeacherInfo(token, 845)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { success ->
                onSuccess(success, token)
            }
    }

    @SuppressLint("CheckResult")
    fun onSuccess(success: Response<ResponseBody>, token: String) {
        if (success.body().toString().contains(accessToken)) {
            IRetrofitLetAuthorize().getRetrofitService().refreshToken(token)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { success -> handleLoginResponse(success) },
                    { errorLiveData.postValue(true) })
        } else {
            if (success.body() == null) {
                tryToRefreshToken()
            } else successAuthLiveData.postValue(true)
        }
    }

    @SuppressLint("CheckResult")
    private fun tryToRefreshToken() {
        IRetrofitLetAuthorize().getRetrofitService().refreshToken(refreshToken)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ success -> handleLoginResponse(success) },
                { errorLiveData.postValue(true) })
    }

    private fun handleLoginResponse(msg: Response<ResponseBody>) {
        val cookieList: List<String> = msg.headers().values("Set-Cookie")
        if (cookieList.size >= 2) {
            val accessToken = cookieList[0].split(";")[0].split("=")[1]
            val refreshToken = cookieList[1].split(";")[0].split("=")[1]
            tokenLiveData.postValue(
                TokenInfo(
                    Companion.accessToken,
                    "${Companion.accessToken}=$accessToken"
                )
            )
            tokenLiveData.postValue(
                TokenInfo(
                    Companion.refreshToken,
                    "${Companion.refreshToken}=$refreshToken"
                )
            )
            successAuthLiveData.postValue(true)
        } else errorLiveData.postValue(true)
    }

}