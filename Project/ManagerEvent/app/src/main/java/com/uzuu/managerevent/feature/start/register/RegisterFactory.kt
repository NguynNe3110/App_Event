package com.uzuu.managerevent.feature.start.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uzuu.managerevent.domain.repository.AuthRepository
import com.uzuu.managerevent.domain.repository.UserRepository

class RegisterFactory(
    private val r : AuthRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(
                r
            ) as T
        }
        throw IllegalArgumentException("unknow viewmodel class")
    }
}