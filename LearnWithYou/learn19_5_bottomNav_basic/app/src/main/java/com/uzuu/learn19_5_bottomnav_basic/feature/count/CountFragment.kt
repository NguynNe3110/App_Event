package com.uzuu.learn19_5_bottomnav_basic.feature.count

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.uzuu.learn19_5_bottomnav_basic.databinding.FragmentCountBinding

class CountFragment: Fragment() {
    private var _binding : FragmentCountBinding ?= null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}