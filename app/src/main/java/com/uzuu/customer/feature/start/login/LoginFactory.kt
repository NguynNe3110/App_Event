package com.uzuu.customer.feature.start.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uzuu.customer.domain.repository.AuthRepository

class LoginFactory(
    private val r: AuthRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                r
            ) as T
        }
        throw IllegalArgumentException("Unknow Viewmodel Class!")
    }
}