package com.uzuu.learn19_5_bottomnav_basic.feature.book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.uzuu.learn19_5_bottomnav_basic.R
import com.uzuu.learn19_5_bottomnav_basic.databinding.FragmentBookBinding
import com.uzuu.learn19_5_bottomnav_basic.feature.book.BookAdapter

class BookFragment : Fragment() {

    // thay vi tao ra onCreateView thi co the truyen nhanh vao contructor cua fragment

    // neu muon dung binding
    private var _binding : FragmentBookBinding ?= null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookBinding.inflate(inflater, container ,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val adapter = BookAdapter(this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->

            when (position) {
                0 -> tab.text = "Find"
                1 -> tab.text = "Registered"
            }

        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}