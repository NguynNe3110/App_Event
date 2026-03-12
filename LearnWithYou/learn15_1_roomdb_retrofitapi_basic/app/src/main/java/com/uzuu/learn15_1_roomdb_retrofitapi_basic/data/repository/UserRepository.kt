package com.uzuu.learn15_1_roomdb_retrofitapi_basic.data.repository

import com.uzuu.learn15_1_roomdb_retrofitapi_basic.data.local.dao.UserDao
import com.uzuu.learn15_1_roomdb_retrofitapi_basic.data.mapper.toDomain
import com.uzuu.learn15_1_roomdb_retrofitapi_basic.data.mapper.toEntity
import com.uzuu.learn15_1_roomdb_retrofitapi_basic.data.remote.UserApi
import com.uzuu.learn15_1_roomdb_retrofitapi_basic.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepository(
    private val api: UserApi,
    private val dao: UserDao
) {
    // 1) UI luôn observe từ DB
    val users: Flow<List<User>> =
        dao.observeAll().map { list -> list.map { it.toDomain() } }

    // 2) Refresh: API -> DB
    suspend fun refreshUsers() {
        val dtos = api.getUsers()
        val entities = dtos.map { it.toEntity() }

        dao.clearAll()
        dao.insertAll(entities)
    }
}