package com.uzuu.learn15_roomdb_retrofitapi.domain.repository

import com.uzuu.learn15_roomdb_retrofitapi.core.result.ApiResult
import com.uzuu.learn15_roomdb_retrofitapi.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    val users: Flow<List<User>>

    // extend nghia la k can tra dl
    suspend fun refreshUsers() : ApiResult<Unit>

    suspend fun deleteAll()
}