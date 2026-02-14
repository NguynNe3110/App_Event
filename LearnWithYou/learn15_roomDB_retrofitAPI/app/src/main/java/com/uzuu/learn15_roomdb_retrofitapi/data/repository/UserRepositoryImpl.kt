package com.uzuu.learn15_roomdb_retrofitapi.data.repository

import com.uzuu.learn15_roomdb_retrofitapi.data.local.dao.UserDao
import com.uzuu.learn15_roomdb_retrofitapi.data.mapper.toDomain
import com.uzuu.learn15_roomdb_retrofitapi.data.mapper.toEntity
import com.uzuu.learn15_roomdb_retrofitapi.data.remote.UserApi
import com.uzuu.learn15_roomdb_retrofitapi.domain.model.User
import com.uzuu.learn15_roomdb_retrofitapi.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    private val api: UserApi,
    private val dao: UserDao
) : UserRepository {

    override val users: Flow<List<User>> =
        dao.observeAll().map { list ->
            list.map { it.toDomain() }
        }

    override suspend fun refreshUsers() {
        val dtos = api.getUsers()
        val entities = dtos.map { it.toEntity() }

        dao.clearAll()
        dao.insertAll(entities)
    }
}