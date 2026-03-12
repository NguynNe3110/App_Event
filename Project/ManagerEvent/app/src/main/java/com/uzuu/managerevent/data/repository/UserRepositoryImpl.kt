package com.uzuu.managerevent.data.repository

import com.uzuu.managerevent.data.local.datasource.UserDataLocalSource
import com.uzuu.managerevent.data.local.entity.UsersEntity
import com.uzuu.managerevent.domain.model.Users
import com.uzuu.managerevent.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(
    private val userLocal : UserDataLocalSource
): UserRepository {
    override val users: Flow<List<Users>>
        get() = TODO("Not yet implemented")

    override suspend fun createUser(user: Users): Long {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(user: Users): Int {
        TODO("Not yet implemented")
    }

    override suspend fun getUserById(id: Int): Users {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUserById(id: Int): Int {
        TODO("Not yet implemented")
    }

    override suspend fun isUserExist(username: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun login(username: String, password: String): UsersEntity? {
        TODO("Not yet implemented")
    }
}