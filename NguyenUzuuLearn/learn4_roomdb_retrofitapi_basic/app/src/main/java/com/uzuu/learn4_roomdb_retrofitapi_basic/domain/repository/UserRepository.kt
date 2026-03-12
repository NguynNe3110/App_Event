package com.uzuu.learn4_roomdb_retrofitapi_basic.domain.repository

import com.uzuu.learn4_roomdb_retrofitapi_basic.core.result.ApiResult
import com.uzuu.learn4_roomdb_retrofitapi_basic.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    val user: Flow<List<User>>

    suspend fun refreshUser() : ApiResult<Unit>

    suspend fun deleteAll()
}