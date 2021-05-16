package com.example.nstustudentapp.profile.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nstustudentapp.R

class ProfileFragment: Fragment() {

    lateinit var mSettings: SharedPreferences
    val APP_PREFERENCES = "userData"
  //  private var presenter: ScreenSaverPresenter? = null
    val TAG = "ScreenSaverView"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initPresenter()
        //    presenter?.tryLogin()
        super.onViewCreated(view, savedInstanceState)
    }


    private fun initPresenter() {
//        presenter = GetStudentPresenterFactory.create(mSettings)
        //  presenter?.attachView(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.profile_layout, null)
    }

    override fun onDestroy() {
  //      presenter?.onDestroy()
        super.onDestroy()
    }
}