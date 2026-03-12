package com.uzuu.learn20_2_recycler_dao.data.repository

import com.uzuu.learn20_2_recycler_dao.data.local.dao.UserDao
import com.uzuu.learn20_2_recycler_dao.data.local.entity.UserEntity
import com.uzuu.learn20_2_recycler_dao.data.mapper.domainToEntity
import com.uzuu.learn20_2_recycler_dao.data.mapper.entityToDomain
import com.uzuu.learn20_2_recycler_dao.domain.model.User
import com.uzuu.learn20_2_recycler_dao.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    private val userDao: UserDao
): UserRepository {

    override val users: Flow<List<User>> =
        userDao.obverseAll().map { listEntity ->
            listEntity.map { it.entityToDomain() }
        }

    override suspend fun deleteAll() : Int {
        return userDao.deleteAll()
    }

    override suspend fun deleteById(id: Int): Int {
        return userDao.deleteById(id)
    }

    override suspend fun getUserById(id: Int): User? {
        return userDao.getUserById(id)?.entityToDomain()
    }

    override suspend fun insert(user: User): Long {
        return userDao.insert(user.domainToEntity())
    }

    override suspend fun update(user: User): Int {
        return userDao.update(user.domainToEntity())
    }

    override suspend fun checkExists(id: Int): Boolean {
        return userDao.checkExists(id)
    }
}