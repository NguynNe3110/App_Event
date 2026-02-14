package com.uzuu.learn15_roomdb_retrofitapi.domain.repository

import com.uzuu.learn15_roomdb_retrofitapi.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    val users: Flow<List<User>>

    suspend fun refreshUsers()
}