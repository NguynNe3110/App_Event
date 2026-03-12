package com.uzuu.learn19_4_practice_nav_isolation.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uzuu.learn19_4_practice_nav_isolation.domain.repository.ProductRepository

class HomeVMFactory(
    private val proRepo: ProductRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(
                proRepo
            ) as T
        }
        throw IllegalArgumentException("unknow viewmodel class!")
    }
}