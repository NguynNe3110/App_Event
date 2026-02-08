package com.uzuu.learn3_roomdb_basic.data

import com.uzuu.learn3_roomdb_basic.data.local.UserDao
import com.uzuu.learn3_roomdb_basic.data.local.UserEntity
import com.uzuu.learn3_roomdb_basic.data.mapper.toDomain
import com.uzuu.learn3_roomdb_basic.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepository(
    private val dao: UserDao
) {
    val users: Flow<List<User>> =
        dao.observerUsers().map { entityList ->
            entityList.map {
                it.toDomain()
            }
        }

    suspend fun seedSample() {
        val item = listOf(
            UserEntity(1, "nguyen"),
            UserEntity(2, "uzuu"),
            UserEntity(3,"lop U")
        )
        dao.insertAll(item)
    }

    suspend fun insertOne(id : Int, name: String){
        if(checknull(id, name)) return
        else dao.insertOne(UserEntity(id, name))
    }

    suspend fun clearAll(){
        dao.clear()
    }

    private fun checknull(id: Int, name: String) : Boolean{
        if(name.isEmpty() || id == -1) return true
        return false
    }
}