package com.uzuu.learn19_5_bottomnav_basic.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uzuu.learn19_5_bottomnav_basic.domain.repository.ProductRepository

class HomeVMFactory(
    private val repo: ProductRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(
                repo
            ) as T
        }
        throw IllegalArgumentException("unknow viewmodel class")
    }
}