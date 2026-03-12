package com.uzuu.managerevent.feature.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.uzuu.managerevent.R
import com.uzuu.managerevent.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding : FragmentMainBinding ?= null
    val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.main_nav_host) as NavHostFragment

        val navController = navHostFragment.navController

        binding.bottomNav.setupWithNavController(navController)
    }
}