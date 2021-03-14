package com.fukuhara.rickandmortyfunapp.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.fukuhara.rickandmortyfunapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_fragment_container) as NavHostFragment
        val navController: NavController = navHostFragment.navController

        setupActionBar(navController)
        setupNavController(navController)
    }

    private fun setupActionBar(navController: NavController) {
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.main_bottom_nav_item_character,
                R.id.main_bottom_nav_item_episode,
                R.id.main_bottom_nav_item_location
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun setupNavController(navController: NavController) {
        val navView = findViewById<BottomNavigationView>(R.id.main_bottom_nav)
        navView.setupWithNavController(navController)
    }
}