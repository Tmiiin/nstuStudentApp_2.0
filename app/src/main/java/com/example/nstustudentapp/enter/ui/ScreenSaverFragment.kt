package com.example.nstustudentapp.enter.ui

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.nstustudentapp.R
import com.example.nstustudentapp.enter.di.GetStudentPresenterFactory
import com.example.nstustudentapp.enter.presentation.ScreenSaverPresenter
import java.lang.Exception

class ScreenSaverFragment : Fragment(), ScreenSaverView {

    lateinit var mSettings: SharedPreferences
    val APP_PREFERENCES = "userData"
    private var presenter: ScreenSaverPresenter? = null
    val TAG = "ScreenSaverView"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter?.tryLogin()
    }

    private fun initPresenter() {
        presenter = GetStudentPresenterFactory.create(mSettings)
        presenter?.attachView(this)
    }

    override fun goToLogInFragment() {
        try {
            Log.e(TAG, "Go to login")
            Navigation.findNavController(requireView())
                .navigate(ScreenSaverFragmentDirections.actionScreenSaverFragmentToLoginFragment())
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
        }
    }

    override fun goToScheduleFragment() {
        try {
            Log.e(TAG, "Go to schedule")
            Navigation.findNavController(requireView())
                .navigate(ScreenSaverFragmentDirections.actionScreenSaverFragmentToNavigationSchedule())
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.screen_saver_layout, container, false)
        mSettings = context?.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)!!
        initPresenter()
        return view
    }

    override fun onDestroy() {
        presenter?.onDestroy()
        super.onDestroy()
    }

  /*  //to hide bottomnav
    override fun onAttach(context: Context) {
        (activity as MainActivity).hideBottomNavigation()
        super.onAttach(context)
    }*/

}