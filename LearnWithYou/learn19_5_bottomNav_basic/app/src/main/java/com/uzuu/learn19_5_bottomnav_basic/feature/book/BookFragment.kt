package com.uzuu.learn19_5_bottomnav_basic.feature.book

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.uzuu.learn19_5_bottomnav_basic.R
import com.uzuu.learn19_5_bottomnav_basic.databinding.FragmentBookBinding
import com.uzuu.learn19_5_bottomnav_basic.feature.book.BookAdapter

class BookFragment : Fragment(R.layout.fragment_book) {

    // thay vi tao ra onCreateView thi co the truyen nhanh vao contructor cua fragment

    // neu muon dung binding
//    private var _binding : FragmentBookBinding ?= null
//    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)

        val adapter = BookAdapter(this)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->

            when (position) {
                0 -> tab.text = "Find"
                1 -> tab.text = "Registered"
            }

        }.attach()
    }
}