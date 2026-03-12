package com.uzuu.learn19_navigation_basic

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val args: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textView = view.findViewById<TextView>(R.id.txtName)

        textView.text = "Hello ${args.userName}"
    }
}
