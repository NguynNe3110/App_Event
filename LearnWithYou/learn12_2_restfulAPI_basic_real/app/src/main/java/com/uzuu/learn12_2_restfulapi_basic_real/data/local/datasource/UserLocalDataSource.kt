package com.uzuu.learn12_2_restfulapi_basic_real.data.local.datasource

import com.uzuu.learn12_2_restfulapi_basic_real.data.local.dao.UsersDao
import com.uzuu.learn12_2_restfulapi_basic_real.data.local.entity.UsersEntity

class UserLocalDataSource(
    private val userDao: UsersDao
) {

    fun observeUsers() = userDao.observeUsers()

    suspend fun checkUserExists(id: Int): Boolean {
        return userDao.checkUserExists(id)
    }

    suspend fun createUser(entity: UsersEntity): Long {
        return userDao.createUser(entity)
    }

    suspend fun deleteAllUser(): Int {
        return userDao.deleteAllUser()
    }

    suspend fun deleteUserById(id: Int): Int {
        return userDao.deleteUserById(id)
    }

    suspend fun deleteUserByUsername(username: String): Int{
        return userDao.deleteUserByUsername(username)
    }

    suspend fun getUserById(id: Int): UsersEntity? {
        return userDao.getUserById(id)
    }

    suspend fun updateUser(user: UsersEntity): Int {
        return userDao.updateUser(user)
    }

    suspend fun InsertAllUser(users: List<UsersEntity>) {
        return userDao.InsertAllUser(users)
    }
}