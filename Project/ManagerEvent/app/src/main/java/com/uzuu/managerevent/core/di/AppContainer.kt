package com.uzuu.managerevent.core.di

import android.content.Context
import com.uzuu.managerevent.data.local.AppDatabase
import com.uzuu.managerevent.data.local.datasource.UserDataLocalSource
import com.uzuu.managerevent.data.remote.RetrofitProvider
import com.uzuu.managerevent.data.remote.datasource.AuthRemoteDataSource
import com.uzuu.managerevent.data.repository.AuthRepositoryImpl
import com.uzuu.managerevent.data.repository.UserRepositoryImpl

class AppContainer(context: Context) {
    private val db = AppDatabase.get(context)

    val authApi = RetrofitProvider.authApi
    val userLocal = UserDataLocalSource(db.userDao())
    val authRemote = AuthRemoteDataSource(authApi = authApi )

    val userRepo  = UserRepositoryImpl(userLocal)

    val authRepo = AuthRepositoryImpl(authRemote)
}