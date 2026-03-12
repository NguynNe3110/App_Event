package com.uzuu.learn19_4_practice_nav_isolation.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uzuu.learn19_4_practice_nav_isolation.domain.repository.ProductRepository
import com.uzuu.learn19_4_practice_nav_isolation.feature.main.HomeViewModel

class DetailVMFactory(
    private val repository: ProductRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(
                repository
            ) as T
        }
        throw IllegalArgumentException("unknow viewmodel class!")
    }
}