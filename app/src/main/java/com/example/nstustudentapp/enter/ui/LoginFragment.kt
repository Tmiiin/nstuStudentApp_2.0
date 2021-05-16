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
        ViewModelProvider(this).get(AuthViewModel::class.java) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mSettings = context?.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE)!!
        val token = mSettings.getString(AuthViewModel.accessToken, "")
        val refreshToken = mSettings.getString(AuthViewModel.refreshToken, "")
        authViewModel.setRefreshToken(refreshToken!!)
        if(token!!.isNotEmpty())
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

    private fun onLoginButtonClick() {
        val login = edit_login.text.toString()
        val password = edit_password.text.toString()
        Log.d(TAG, "Trying to log in with password: $password and login: $login")
        authViewModel.tryLogin(login, password)
    }

    private fun onForgottenPassword() {
        //some code
    }

    private fun showLoginError() {
        Log.d(TAG, "Login error")
        if (!::errorDialog.isInitialized) {
            createAlertDialog()
        }
        if (!isShown)
                isShown = true
                errorDialog.show()
    }

    private fun goToScheduleFragment() {
        Log.d(TAG, "go to new activity")
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