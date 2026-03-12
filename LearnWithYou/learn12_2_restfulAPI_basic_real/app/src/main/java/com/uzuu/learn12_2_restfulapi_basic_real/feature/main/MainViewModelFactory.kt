package com.uzuu.learn12_2_restfulapi_basic_real.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uzuu.learn12_2_restfulapi_basic_real.domain.repository.BookRepository
import com.uzuu.learn12_2_restfulapi_basic_real.domain.repository.CategoryRepository
import com.uzuu.learn12_2_restfulapi_basic_real.domain.repository.UserRepository

class MainViewModelFactory(
    private val userRepo: UserRepository,
    private val bookRepo: BookRepository,
    private val cateRepo: CategoryRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                userRepo,
                bookRepo,
                cateRepo
            ) as T
        }
        throw IllegalArgumentException("unknown viewmodel class")
    }
}