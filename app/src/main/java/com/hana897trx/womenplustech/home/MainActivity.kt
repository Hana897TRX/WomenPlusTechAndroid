package com.hana897trx.womenplustech.home

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hana897trx.womenplustech.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController

        findViewById<BottomNavigationView>(R.id.bottomNavigationView).setupWithNavController(navController)
        navController.graph = navController.navInflater.inflate(R.navigation.principal_navigation)
    }

    override fun onPause() {
        super.onPause()
        val sp = application.getSharedPreferences("remember-user", Context.MODE_PRIVATE)

        if(sp != null) {
            if(!sp.getBoolean("remember", false)) {
                sp.edit().apply {
                    putString("password", "")
                    apply()
                }
            }
        }
    }
}