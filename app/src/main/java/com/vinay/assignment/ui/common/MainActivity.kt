package com.vinay.assignment.ui.common

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.vinay.assignment.R
import com.vinay.assignment.ui.base.BaseActivity

class MainActivity : BaseActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.fragment)
    }
}
