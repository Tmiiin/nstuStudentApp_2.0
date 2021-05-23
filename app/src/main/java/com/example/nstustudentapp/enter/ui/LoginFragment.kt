package com.example.nstustudentapp.enter.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.nstustudentapp.Constants
import com.example.nstustudentapp.R
import com.example.nstustudentapp.enter.data.model.TokenInfo
import com.example.nstustudentapp.enter.presentation.AuthViewModel
import kotlinx.android.synthetic.main.login_layout.*

class LoginFragment : Fragment() {

    val TAG = "LoginFragment"
    lateinit var mSettings: SharedPreferences
    private lateinit var errorDialog: AlertDialog.Builder
    private var isShown: Boolean = false
    private val authViewModel: AuthViewModel by lazy {
        ViewModelProvider(this).get(AuthViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mSettings = context?.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE)!!
        val token = mSettings.getString(AuthViewModel.accessToken, "")
        Log.i(TAG, "Token is: $token");
        if (token!!.isNotEmpty())
            authViewModel.isTokenValid(token)
        enter_button.setOnClickListener { onLoginButtonClick() }
        forgotten_password.setOnClickListener { onForgottenPassword() }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setToken(tokenInfo: TokenInfo) {
        mSettings.edit().putString(tokenInfo.tokenName, tokenInfo.token).apply()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authViewModel.getErrorLiveData().observe(this, { if (it) showLoginError() })
        authViewModel.getSuccessAuthLiveData().observe(this, { goToScheduleFragment() })
        authViewModel.getTokenLiveData().observe(this, { setToken(it) })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.login_layout, null)
    }

    var login: String? = null
    var password: String? = null
    private fun onLoginButtonClick() {
        login = edit_login.text.toString()
        password = edit_password.text.toString()
        Log.d(TAG, "Trying to log in with password: $password and login: $login")
        authViewModel.tryLogin(login!!, password!!)
    }

    private fun onForgottenPassword() {
        //some code
    }

    private fun showLoginError() {
        Log.d(TAG, "Login error")
        if (mSettings.contains("login") && mSettings.contains("password")) {
            val login = mSettings.getString("login", "")
            val password = mSettings.getString("password", "")
            if (login!!.isNotEmpty() && password!!.isNotEmpty()) {
                authViewModel.tryLogin(login, password)
            } else {
                showLoginEror()
            }
        } else showLoginEror()
    }

    private fun showLoginEror() {
        if (!::errorDialog.isInitialized) {
            createAlertDialog()
        }
        if (!isShown)
            isShown = true
        errorDialog.show()
    }

    private fun goToScheduleFragment() {
        Log.d(TAG, "go to new activity")
        if(!password.isNullOrEmpty() && !login.isNullOrEmpty()){
        val preferencesEditor = mSettings.edit()
        preferencesEditor.putString("login", login)
        preferencesEditor.putString("password", password)
        preferencesEditor.apply()
        }
        Navigation.findNavController(requireView())
            .navigate(LoginFragmentDirections.actionLoginFragmentToScheduleFragment())
    }


    private fun createAlertDialog() {
        errorDialog = context?.let {
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