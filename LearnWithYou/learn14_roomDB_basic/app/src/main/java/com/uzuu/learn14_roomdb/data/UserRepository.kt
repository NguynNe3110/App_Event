package com.uzuu.learn14_roomdb.data

import com.uzuu.learn14_roomdb.data.local.UserDao
import com.uzuu.learn14_roomdb.data.local.UserEntity
import com.uzuu.learn14_roomdb.domain.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepository(
    private val dao: UserDao
) {
    // Room -> Flow<List<UserEntity>>
    // Repository map -> Flow<List<User>>
    val users: Flow<List<User>> =
//        dao.observeUsers().map { list ->
//            list.map { it.toDomain() }
//        }
        dao.observeUsers().map { entityList ->
            entityList.map { entity ->
                entity.toDomain()
            }
        }

    suspend fun seedSample() {
        val sample = listOf(
            UserEntity(1, "Nguyen"),
            UserEntity(2, "Uzuu"),
            UserEntity(3, "Room is cool")
        )
        dao.insertAll(sample)
    }

    suspend fun clearAll() {
        dao.clear()
    }

    private fun UserEntity.toDomain(): User {
        return User(
            id = id,
            displayName = name
        )
    }
}
