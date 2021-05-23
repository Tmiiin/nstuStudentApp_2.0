package com.example.nstustudentapp.schedule.presentation

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nstustudentapp.enter.data.model.ScheduleResponseModel
import com.example.nstustudentapp.schedule.data.model.LessonsOnDay
import com.example.nstustudentapp.schedule.domain.GetScheduleUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.lang.Exception
import java.net.URL
import java.net.URLEncoder

class ScheduleViewModel(
    private val getScheduleUseCase: GetScheduleUseCase
): ViewModel() {

    var myDisposable: Disposable? = null
    val TAG: String = "ScheduleViewModel"
    val teacherPhotoLiveData = MutableLiveData<Drawable?>()
    val listOfLessonsLiveData = MutableLiveData<ArrayList<LessonsOnDay>>()

    fun getSchedule(group: String) {
        if(group.isNotEmpty()){
            try {
                val groupUTF8 = URLEncoder.encode(group, "UTF-8")
                myDisposable = groupUTF8.let {
                    getScheduleUseCase(it)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe({ success -> handleLoginResponse(success) },
                            { error -> doOnError(error) })
                }
            } catch (e: Exception) {
                Log.e(TAG, e.message.toString())
            }
        }
        else doOnError(Throwable("Group wasn't choosen"))
    }

    private fun doOnError(e: Throwable) {
        Log.e(TAG, e.message.toString())
       // view?.showError("Не вышло получить расписание")
    }

    fun loadImage(url: String) {
        listOfLessonsLiveData.postValue(null)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val inputStream: InputStream = URL(url).content as InputStream
                    val drawable = Drawable.createFromStream(inputStream, null)
                    teacherPhotoLiveData.postValue(drawable)
                } catch (e: Exception) {
                    Log.e(TAG, e.message!!)
                }
            }
        }
    }

    private fun handleLoginResponse(msg: ScheduleResponseModel) {
        Log.i(TAG, "token is: ${msg.responseBody}")
        listOfLessonsLiveData.postValue(msg.responseBody)
    }

    fun onDestroy() {
        if(myDisposable != null) {
            this.myDisposable?.dispose()
            this.myDisposable = null
        }
    }
}