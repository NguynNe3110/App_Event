package com.uzuu.learn19_5_bottomnav_basic.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uzuu.learn19_5_bottomnav_basic.domain.repository.ProductRepository
import com.uzuu.learn19_5_bottomnav_basic.feature.profile.ProfileViewModel

class SearchVMFactory(
    private val r : ProductRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(
                r
            ) as T
        }
        throw IllegalArgumentException("unknow viewmodel class")
    }
}