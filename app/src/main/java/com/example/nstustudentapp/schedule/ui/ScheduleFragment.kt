package com.example.nstustudentapp.schedule.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nstustudentapp.R
import com.example.nstustudentapp.enter.ui.MainActivity
import com.example.nstustudentapp.schedule.data.model.Lesson
import com.example.nstustudentapp.schedule.di.SchedulePresenterFactory
import com.example.nstustudentapp.schedule.presentation.LessonAdapter
import com.example.nstustudentapp.schedule.presentation.SchedulePresenter
import kotlinx.android.synthetic.main.schedule_layout.*

class ScheduleFragment : Fragment(), LessonAdapter.OnLessonListener {


    lateinit var mSettings: SharedPreferences
    private var listOfLessons: MutableList<Lesson> =
        arrayListOf() //list of each lesson-views in schedule
    private var presenter: SchedulePresenter? = null
    lateinit var lessonAdapter: LessonAdapter
    val TAG = "ScreenSaverView"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgressBar()
        lessonAdapter = context?.let { LessonAdapter(listOfLessons, this, it) }!!
        schedule_rv_lessons.adapter = lessonAdapter
        schedule_rv_lessons.layoutManager = LinearLayoutManager(context)
        // presenter?.getLoanList()
        schedule_rv_lessons.adapter = lessonAdapter
    }

    fun setListOfLesson(list: List<Lesson>) {
        this.listOfLessons.clear()
        this.listOfLessons.addAll(list)
        lessonAdapter.notifyDataSetChanged()
    }

    private fun initPresenter() {
           presenter = SchedulePresenterFactory.create(mSettings)
           presenter?.attachView(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.schedule_layout, null)
    }

    override fun onDestroy() {
        presenter?.onDestroy()
        super.onDestroy()
    }

    override fun onAttach(context: Context) {
        (activity as MainActivity).showBottomNavigation()
        super.onAttach(context)
    }

    private fun showProgressBar() {
        schedule_rv_lessons.visibility = View.INVISIBLE
        schedule_text_data_is_empty.visibility = View.INVISIBLE
        schedule_progress_bar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        schedule_progress_bar.visibility = View.INVISIBLE
        if (listOfLessons.size == 0)
            schedule_text_data_is_empty.visibility = View.VISIBLE
        else schedule_rv_lessons.visibility = View.VISIBLE
    }

    override fun onLessonClick(v: View?, position: Int) {
        TODO("Not yet implemented")
    }

}