package com.uzuu.managerevent.feature.first

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.uzuu.managerevent.R
import com.uzuu.managerevent.data.session.SessionManager
import com.uzuu.managerevent.databinding.FragmentSplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {
    private var _binding : FragmentSplashBinding ?= null

    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            delay(4000)

            val test = "nguyen"
            val token = test//SessionManager.getToken()

            if (token != null) {
                findNavController().navigate(R.id.action_splash_to_home)
            } else {
                findNavController().navigate(R.id.action_splash_to_login)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}