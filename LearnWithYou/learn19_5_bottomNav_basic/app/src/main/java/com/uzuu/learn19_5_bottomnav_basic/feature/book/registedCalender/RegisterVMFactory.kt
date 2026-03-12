package com.uzuu.learn19_5_bottomnav_basic.feature.book.registedCalender

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uzuu.learn19_5_bottomnav_basic.domain.repository.TimeRepository
import com.uzuu.learn19_5_bottomnav_basic.feature.book.findCalender.FindViewModel

class RegisterVMFactory(
    private val r : TimeRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(
                r
            ) as T
        }
        throw IllegalArgumentException("unknow viewmodel class")
    }
}