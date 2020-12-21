package com.example.nstustudentapp.enter.ui

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.nstustudentapp.R
import com.example.nstustudentapp.enter.di.GetStudentPresenterFactory
import com.example.nstustudentapp.enter.di.LoginPresenterFactory
import com.example.nstustudentapp.enter.presentation.ScreenSaverPresenter

class ScreenSaverFragment : Fragment(), ScreenSaverView{

    lateinit var mSettings: SharedPreferences
    val APP_PREFERENCES = "userData"
    private var presenter: ScreenSaverPresenter? = null
    val TAG = "ScreenSaverView"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mSettings = context?.getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)!!
        initPresenter()
        presenter?.tryLogin()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initPresenter() {
        presenter = GetStudentPresenterFactory.create(mSettings)
        presenter?.attachView(this)
    }

    override fun goToLogInFragment(){
        val action = R.id.action_screenSaverFragment_to_loginFragment
        view?.let { Navigation.findNavController(it).navigate(action) }
    }

    override fun goToScheduleFragment(){

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.screen_saver_layout, null)
    }

    override fun onDestroy() {
        presenter?.onDestroy()
        super.onDestroy()
    }

}