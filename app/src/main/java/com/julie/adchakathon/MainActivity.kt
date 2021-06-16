package com.julie.adchakathon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.julie.adchakathon.databinding.ActivityMainBinding
import com.julie.adchakathon.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupViews()

        if (!Constants.hasLocationPermission(this)) {
            startActivity(Intent(this, PermissionActivity::class.java))
        }
    }

    private fun setupViews() {
        navController = Navigation.findNavController(this, R.id.nav_host_container)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_container).navigateUp() || super.onSupportNavigateUp()
    }
}