package com.example.nstustudentapp.enter.ui

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.nstustudentapp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()
    }

    fun setupViews() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.frag_nav_host) as NavHostFragment
        navController = navHostFragment.navController
        bottom_nav_view.setupWithNavController(navController)
        hideBottomNavigation()
    }

    fun showBottomNavigation() {
        bottom_nav_view.visibility = View.VISIBLE
    }

    fun hideBottomNavigation() {
        bottom_nav_view.visibility = View.GONE
    }

    private var backPressedOnce = false

    override fun onBackPressed() {
        if (R.id.navigation_schedule == navController.currentDestination?.id) {
            if (backPressedOnce) {
                super.onBackPressed()
                return
            }

            backPressedOnce = true
            Toast.makeText(this, "Press BACK again to exit", Toast.LENGTH_SHORT).show()

            Handler().postDelayed(2000) {
                backPressedOnce = false
            }
        } else {
            super.onBackPressed()
        }
    }
}