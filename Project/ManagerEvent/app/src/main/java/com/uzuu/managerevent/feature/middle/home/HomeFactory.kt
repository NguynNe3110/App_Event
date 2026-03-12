package com.uzuu.managerevent.feature.middle.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uzuu.managerevent.domain.repository.EventRepository

class HomeFactory(
    private val r : EventRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(
                r
            ) as T
        }
        throw IllegalArgumentException("Unknow Viewmodel Class!")
    }
}