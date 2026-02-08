package com.uzuu.learn3_roomdb_basic.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uzuu.learn3_roomdb_basic.data.UserRepository

class MainViewModelFactory(
    private val repo: UserRepository
) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create (modelClass: Class<T>) : T{
        if(modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}