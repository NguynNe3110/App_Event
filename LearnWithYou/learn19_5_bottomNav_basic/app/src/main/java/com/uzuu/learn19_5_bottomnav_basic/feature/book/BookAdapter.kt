package com.uzuu.learn19_5_bottomnav_basic.feature.book

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.uzuu.learn19_5_bottomnav_basic.feature.book.findCalender.FindFragment
import com.uzuu.learn19_5_bottomnav_basic.feature.book.registedCalender.RegistedFragment

class BookAdapter(fragment: Fragment)
    : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> FindFragment()
            else -> RegistedFragment()
        }

    }
}