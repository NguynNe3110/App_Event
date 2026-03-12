package com.uzuu.learn19_5_bottomnav_basic.feature.book.registedCalender.detailRegister

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uzuu.learn19_5_bottomnav_basic.domain.repository.TimeRepository
import com.uzuu.learn19_5_bottomnav_basic.feature.book.findCalender.FindViewModel

class DetailFactory(
    private val ra : TimeRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(
                ra
            ) as T
        }
        throw IllegalArgumentException("unknow viewmodel class")
    }
}