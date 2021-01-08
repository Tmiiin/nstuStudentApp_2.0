package com.example.nstustudentapp.schedule.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.nstustudentapp.R
import com.example.nstustudentapp.enter.di.GetStudentPresenterFactory
import com.example.nstustudentapp.enter.presentation.ScreenSaverPresenter
import com.example.nstustudentapp.enter.ui.MainActivity
import kotlinx.android.synthetic.main.activity_main.*

class ScheduleFragment: Fragment() {

    lateinit var mSettings: SharedPreferences
    val APP_PREFERENCES = "userData"
    private var presenter: ScreenSaverPresenter? = null
    val TAG = "ScreenSaverView"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
     //   initPresenter()
    //    presenter?.tryLogin()
//        NavigationUI.setupWithNavController(bottom_nav_view, findNavController())
        super.onViewCreated(view, savedInstanceState)
    }


    private fun initPresenter() {
     //   presenter = GetStudentPresenterFactory.create(mSettings)
      //  presenter?.attachView(this)
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
}