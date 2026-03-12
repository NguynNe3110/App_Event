package com.uzuu.learn19_4_practice_nav_isolation.feature.listview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uzuu.learn19_4_practice_nav_isolation.domain.repository.ProductRepository
import com.uzuu.learn19_4_practice_nav_isolation.feature.detail.DetailViewModel
import com.uzuu.learn19_4_practice_nav_isolation.feature.main.HomeViewModel

class ListVMFactory(
    private val repository: ProductRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ListViewModel::class.java)) {
            return ListViewModel(
                repository
            ) as T
        }
        throw IllegalArgumentException("unknow viewmodel class!")
    }
}