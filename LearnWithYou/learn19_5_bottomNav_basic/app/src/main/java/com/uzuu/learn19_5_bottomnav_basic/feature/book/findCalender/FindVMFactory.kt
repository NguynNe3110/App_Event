package com.uzuu.learn19_5_bottomnav_basic.feature.book.findCalender

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uzuu.learn19_5_bottomnav_basic.domain.repository.TimeRepository

class FindVMFactory(
    private val r : TimeRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FindViewModel::class.java)) {
            return FindViewModel(
                r
            ) as T
        }
        throw IllegalArgumentException("unknow viewmodel class")
    }
}