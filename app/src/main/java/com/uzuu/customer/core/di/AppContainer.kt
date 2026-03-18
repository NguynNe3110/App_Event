package com.uzuu.customer.core.di

import android.content.Context
import com.uzuu.customer.data.local.AppDatabase
import com.uzuu.customer.data.local.datasource.UserDataLocalSource
import com.uzuu.customer.data.remote.RetrofitProvider
import com.uzuu.customer.data.remote.datasource.AuthRemoteDataSource
import com.uzuu.customer.data.repository.AuthRepositoryImpl
import com.uzuu.customer.data.repository.UserRepositoryImpl

class AppContainer(context: Context) {
    private val db = AppDatabase.get(context)

    val authApi = RetrofitProvider.authApi
    val userLocal = UserDataLocalSource(db.userDao())
    val authRemote = AuthRemoteDataSource(authApi = authApi)

    val userRepo  = UserRepositoryImpl(userLocal)

    val authRepo = AuthRepositoryImpl(authRemote)
}