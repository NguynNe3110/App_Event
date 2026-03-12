package com.uzuu.learn19_5_bottomnav_basic.feature.book.findCalender

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class FindFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val text = TextView(requireContext())
        text.text = "Find Schedule Screen"
        text.textSize = 24f

        return text
    }
}