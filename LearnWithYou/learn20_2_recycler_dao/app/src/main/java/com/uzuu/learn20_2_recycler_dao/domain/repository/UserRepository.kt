package com.uzuu.learn20_2_recycler_dao.domain.repository

import com.uzuu.learn20_2_recycler_dao.data.local.dao.UserDao
import com.uzuu.learn20_2_recycler_dao.data.local.entity.UserEntity
import com.uzuu.learn20_2_recycler_dao.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    val users: Flow<List<User>>

    suspend fun insert(user: User) : Long

    suspend fun update(user: User) : Int

    suspend fun deleteAll(): Int

    suspend fun deleteById(id: Int): Int

    //ở dao cho pheép lấy null thì 1 là xử lý, 2 là cho phép null
    suspend fun getUserById(id: Int) : User?

    suspend fun checkExists(id: Int) : Boolean
}