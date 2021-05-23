package com.example.nstustudentapp.schedule.ui

import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nstustudentapp.Constants
import com.example.nstustudentapp.R
import com.example.nstustudentapp.enter.presentation.AuthViewModel
import com.example.nstustudentapp.schedule.data.model.Lesson
import com.example.nstustudentapp.schedule.data.model.Teacher
import com.example.nstustudentapp.schedule.data.network.IRetrofitSchedule
import com.example.nstustudentapp.schedule.di.SchedulePresenterFactory
import com.example.nstustudentapp.schedule.presentation.LessonAdapter
import com.example.nstustudentapp.schedule.presentation.ScheduleViewModel
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.recycler_schedule_layout.*
import kotlinx.android.synthetic.main.teacher_dialog_view.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.InputStream
import java.lang.Exception
import java.net.URL

class LessonsFragment : Fragment() {

    private lateinit var lessonAdapter: LessonAdapter
    private val TAG = "LessonFragment ${this.tag}"
    private lateinit var mScheduleViewModel: ScheduleViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mScheduleViewModel = SchedulePresenterFactory.create()
        return inflater.inflate(R.layout.recycler_schedule_layout, container, false);
    }

    val week = 8
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lessonAdapter = LessonAdapter({ onLessonClick(it) }, week)
        lesson_rv.adapter = lessonAdapter
        lesson_rv.layoutManager = LinearLayoutManager(activity);
        showProgressBar()
    }

    private fun onLessonClick(lesson: Lesson) {
        if(lesson.teacherInfo.isNotEmpty()) {
            val teacherInfo = lesson.teacherInfo[0]
            val teacherId = Integer.parseInt(teacherInfo.teacherUrl.split("/").last())
            val prefs =
                context?.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE)!!
            val accessToken = prefs.getString(AuthViewModel.accessToken, "")
            val retrofitTeacher =
                IRetrofitSchedule().getRetrofitTeacherService()
                    .getTeacherInfo(accessToken!!, teacherId)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ success -> createAlertDialog(success) },
                        { })
        }
    }

    private fun createAlertDialog(response: Response<ResponseBody>) {
        val mLayoutInflater = LayoutInflater.from(context)
        val customDialogView: View = mLayoutInflater.inflate(
            R.layout.teacher_dialog_view, null
        )
        val teacherInfoString = response.body()?.string()?.replace("\n", "")
        val teacherInfoString2 = teacherInfoString?.substring(1, teacherInfoString.length - 1)
        val teacherInfo = Gson().fromJson(teacherInfoString2, Teacher::class.java)
        val surnameAndPatronymic = "${teacherInfo.surname} ${teacherInfo.patronymic}"
        val teacherDegree = teacherInfo.degree + " " + teacherInfo.post

        val teacherInfoDialog = AlertDialog.Builder(context).setView(customDialogView).create()
        teacherInfoDialog.setOnShowListener {
            customDialogView.dialog_teacher_surname.text = surnameAndPatronymic
            customDialogView.dialog_teacher_name.text = teacherInfo.name
            customDialogView.teacher_rank.text = teacherDegree
            mScheduleViewModel.teacherPhotoLiveData.observe(viewLifecycleOwner, {
                if (it != null) {
                    customDialogView.teacher_photo_progress.visibility = View.GONE
                    customDialogView.teacher_photo.setImageDrawable(it)
                } else {
                    customDialogView.teacher_photo_progress.visibility = View.VISIBLE
                    customDialogView.teacher_photo.visibility = View.GONE
                }
            })
            if (teacherInfo.portrait_url!!.isNotEmpty())
                mScheduleViewModel.loadImage(teacherInfo.portrait_url!!)
            else customDialogView.teacher_photo.setImageDrawable(
                ContextCompat.getDrawable(customDialogView.context, R.drawable.royak)
            )
        }
        teacherInfoDialog.setOnCancelListener { mScheduleViewModel.teacherPhotoLiveData.removeObservers(viewLifecycleOwner) }
        teacherInfoDialog.show()
    }

    private fun showProgressBar() {
        lesson_rv.visibility = View.GONE
        schedule_text_data_is_empty.visibility = View.GONE
        schedule_progress_bar.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        schedule_progress_bar.visibility = View.GONE
        if (lessonAdapter.itemCount == 0)
            schedule_text_data_is_empty.visibility = View.VISIBLE
        else {
            schedule_text_data_is_empty.visibility = View.GONE
            lesson_rv.visibility = View.VISIBLE
        }
    }

    fun setListOfLesson(list: List<Lesson>) {
        lessonAdapter.setData(list)
        hideProgressBar()
    }

}