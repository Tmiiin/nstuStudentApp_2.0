package com.example.nstustudentapp.schedule.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.nstustudentapp.Constants
import com.example.nstustudentapp.R
import com.example.nstustudentapp.enter.ui.MainActivity
import com.example.nstustudentapp.schedule.data.model.Lesson
import com.example.nstustudentapp.schedule.data.model.LessonsOnDay
import com.example.nstustudentapp.schedule.di.SchedulePresenterFactory
import com.example.nstustudentapp.schedule.presentation.SchedulePresenter
import kotlinx.android.synthetic.main.schedule_layout.*
import java.lang.Exception

class ScheduleFragment : Fragment(){

    lateinit var mSettings: SharedPreferences
    private var mapOfLessons: MutableMap<String, List<Lesson>> =
        hashMapOf()
    private lateinit var presenter: SchedulePresenter
    val TAG = "ScreenSaverView"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mSettings = context?.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE)!!
        initPresenter()
        presenter.getSchedule("%D0%9F%D0%9C-71")
    }

    fun setMapOfLessons(map: ArrayList<LessonsOnDay>){
        for(item in map) {
            this.mapOfLessons[item.day] = item.lessons
        }
        recycler_custom_view.setListOfLesson(mapOfLessons["пн"]!!)
      //  recyclerStateView.hideProgressBar()
    }

    private fun initPresenter() {
        try {
            presenter = SchedulePresenterFactory.create(mSettings)
            presenter.attachView(this)
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
        }
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

    fun showError(error: String){
        recycler_custom_view.hideProgressBar()
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

}