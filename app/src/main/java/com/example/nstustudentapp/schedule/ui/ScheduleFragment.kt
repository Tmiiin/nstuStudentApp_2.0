package com.example.nstustudentapp.schedule.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.nstustudentapp.Constants
import com.example.nstustudentapp.R
import com.example.nstustudentapp.enter.ui.MainActivity
import com.example.nstustudentapp.schedule.data.model.Lesson
import com.example.nstustudentapp.schedule.data.model.LessonsOnDay
import com.example.nstustudentapp.schedule.di.SchedulePresenterFactory
import com.example.nstustudentapp.schedule.presentation.SchedulePresenter
import com.example.nstustudentapp.schedule.presentation.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.schedule_layout.*


class ScheduleFragment : Fragment() {

    lateinit var mSettings: SharedPreferences
    private lateinit var presenter: SchedulePresenter
    val TAG = "ScreenSaverView"
    val groupList = listOf("ПМ-71", "ПМ-72", "ПМ-81")
val adapter = createViewPagerAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mSettings = context?.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE)!!
        initPresenter()
        initSpinner()
        view_pager.adapter = adapter
        TabLayoutMediator(
            tab_layout, view_pager
        ) { tab, position ->
            tab.setCustomView(R.layout.days_of_week_schedule)
         }
            .attach();
    }

    private fun createViewPagerAdapter(): ViewPagerAdapter {
        val adapter = ViewPagerAdapter(this);
        return adapter;
    }

    private fun initSpinner() {
        ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, groupList)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner_group.adapter = adapter
            }
        spinner_group.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                presenter.getSchedule("ПМ-71")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Log.i(TAG, "Selected item is: ${spinner_group.selectedItem}")
                //    day_list_view.showProgressBar()
                presenter.getSchedule(spinner_group.selectedItem.toString())
            }
        }
    }

    val listOfLessonsMap = hashMapOf<String, List<Lesson>>()
    fun setMapOfLessons(map: ArrayList<LessonsOnDay>) {
        for (item in map) {
            listOfLessonsMap[item.day] = item.lessons
        }
        Log.i(TAG, "Set map of: $listOfLessonsMap")
        adapter.lessonFragment.setListOfLesson(listOfLessonsMap["пн"]!!)
        //   day_list_view.setData(thisMap)
    }

    public fun getListOfLesson(position: String): List<Lesson>? {
        return listOfLessonsMap[position]
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
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun onAttach(context: Context) {
        (activity as MainActivity).showBottomNavigation()
        super.onAttach(context)
    }

    fun showError(error: String) {
        //  day_list_view.hideProgressBar()
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

}

