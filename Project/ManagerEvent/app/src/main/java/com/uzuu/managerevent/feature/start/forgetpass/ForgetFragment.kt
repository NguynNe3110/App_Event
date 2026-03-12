package com.uzuu.managerevent.feature.start.forgetpass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.uzuu.managerevent.databinding.FragmentForgetPasswordBinding

class ForgetFragment: Fragment() {
    private var _bindind : FragmentForgetPasswordBinding ?= null

    val binding get() = _bindind!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bindind = FragmentForgetPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _bindind = null
    }

    private fun setupButton() {

    }
}