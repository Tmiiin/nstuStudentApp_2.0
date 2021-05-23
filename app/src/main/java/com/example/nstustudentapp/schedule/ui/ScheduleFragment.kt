package com.example.nstustudentapp.schedule.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.nstustudentapp.Constants
import com.example.nstustudentapp.R
import com.example.nstustudentapp.enter.ui.MainActivity
import com.example.nstustudentapp.schedule.data.model.Days
import com.example.nstustudentapp.schedule.data.model.Lesson
import com.example.nstustudentapp.schedule.data.model.LessonsOnDay
import com.example.nstustudentapp.schedule.di.SchedulePresenterFactory
import com.example.nstustudentapp.schedule.presentation.DateUtils
import com.example.nstustudentapp.schedule.presentation.ScheduleViewModel
import com.example.nstustudentapp.schedule.presentation.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.textview.MaterialTextView
import kotlinx.android.synthetic.main.days_of_week_schedule.*
import kotlinx.android.synthetic.main.schedule_layout.*

class ScheduleFragment : Fragment() {

    lateinit var mSettings: SharedPreferences
    private lateinit var presenter: ScheduleViewModel
    val TAG = "ScreenSaverView"
    val groupList = listOf("ПМ-71", "ПМ-72", "ПМ-81")
    lateinit var adapter: ViewPagerAdapter
    lateinit var dateUtils: DateUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dateUtils = DateUtils()
        mSettings = context?.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE)!!
        initPresenter()
        initSpinner()
        adapter = ViewPagerAdapter(this, 6);
        view_pager.adapter = adapter
        view_pager.offscreenPageLimit = 6
        TabLayoutMediator(
            tab_layout, view_pager
        ) { tab, position ->
            run {
                val dayView = layoutInflater.inflate(R.layout.days_of_week_schedule, null, false)
                val textDay = dayView.findViewById<TextView>(R.id.calendar_day)
                textDay.text = Days.values()[position].day
                tab.customView = dayView
            }
            dateUtils.getCurrentWeek()
        }
            .attach()
        view_pager.registerOnPageChangeCallback(schedulePageChangeCallback)
        presenter.listOfLessonsLiveData.observe(viewLifecycleOwner, {
            setMapOfLessons(it)
        })
        dateUtils.dateLiveData.observe(viewLifecycleOwner, {
            for(i in 0 until tab_layout.tabCount)
                ((tab_layout.getTabAt(i)?.customView as LinearLayout)
                    .getChildAt(0) as MaterialTextView).text = it[i]
        })
        last_week.setOnClickListener { dateUtils.getPreviousWeek() }
    }

    var schedulePageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            if(listOfLessonsMap.containsKey(Days.values()[position].day))
                (childFragmentManager.findFragmentByTag("f" + view_pager.currentItem) as LessonsFragment)
                    .setListOfLesson(listOfLessonsMap[Days.values()[position].day]!!)
        }
    }

    override fun onDetach() {
        view_pager.unregisterOnPageChangeCallback(schedulePageChangeCallback)
        super.onDetach()
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

    private val listOfLessonsMap = hashMapOf<String, List<Lesson>>()
    private fun setMapOfLessons(map: ArrayList<LessonsOnDay>) {
        for (item in map) {
            listOfLessonsMap[item.day] = item.lessons
        }
        Log.i(TAG, "Set map of: $listOfLessonsMap")
        (childFragmentManager.findFragmentByTag("f" + view_pager.currentItem) as LessonsFragment)
                .setListOfLesson(listOfLessonsMap[Days.values()[0].day]!!)
        adapter.notifyDataSetChanged()
    }

    private fun initPresenter() {
        try {
            presenter = SchedulePresenterFactory.create()
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
       // (childFragmentManager.findFragmentByTag("f" + view_pager.currentItem) as LessonsFragment).hideProgressBar()
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

}

