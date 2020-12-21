package com.example.nstustudentapp.enter.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.nstustudentapp.R
import com.example.nstustudentapp.enter.di.LoginPresenterFactory
import com.example.nstustudentapp.enter.presentation.AuthPresenter
import kotlinx.android.synthetic.main.login_layout.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragment: Fragment() {

    val TAG = "AuthActivity"
    lateinit var mSettings: SharedPreferences
    val APP_PREFERENCES = "userData"

    private lateinit var build: AlertDialog.Builder
    private lateinit var presenter: AuthPresenter
    private var isShown: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mSettings = context?.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)!!
        initPresenter()
        enter_button.setOnClickListener { onLoginButtonClick() }
        forgotten_password.setOnClickListener { onForgottenPassword() }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initPresenter() {
        presenter = LoginPresenterFactory.create()
        presenter.attachView(this)
    }

    fun setToken(token: String){
        mSettings.edit().putString("tokenID", token).apply()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.login_layout, null)
    }

    private fun onLoginButtonClick() {
        val login = edit_login.text.toString()
        val password = edit_password.text.toString()
        Log.d(TAG, "trying to log in")
        presenter.tryLogin(login + getString(R.string.domain), password)
    }

    private fun onForgottenPassword() {
        //some code
    }

    fun showLoginError() {
        Log.d(TAG, "you should see login error")
        if (!::build.isInitialized) {
            createAlertDialog()
        }
        if (!isShown)
            CoroutineScope(Dispatchers.Main).launch {
                isShown = true
                build.show()
            }
    }

    fun goToScheduleFragment() {
        Log.d(TAG, "go to new activity")
    }


    private fun createAlertDialog() {
        build = context?.let {
            AlertDialog.Builder(it)
                .setMessage(R.string.error_login_message)
                .setPositiveButton(R.string.ok) { dialog, _ ->
                    dialog.cancel()
                    isShown = false
                }
                .setCancelable(false)
        }!!
    }
}