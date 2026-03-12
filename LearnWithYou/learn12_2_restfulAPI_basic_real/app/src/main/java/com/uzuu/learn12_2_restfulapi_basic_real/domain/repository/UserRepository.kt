package com.uzuu.learn12_2_restfulapi_basic_real.domain.repository

import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.user.CreateUserRequest
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.user.UpdateUserRequest
import com.uzuu.learn12_2_restfulapi_basic_real.domain.model.user.Users
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    val users: Flow<List<Users>>

    // k nên dùng CreateUserRequest , mà nên mapper ở repo
    suspend fun createUser(request: CreateUserRequest): Long

    // domain k nên biết về dto

    //thường là model của network layer (DTO).
    //Nhưng Repository interface thuộc domain layer.
    suspend fun updatePutUser(
        username: String,
        request: UpdateUserRequest) : Int

    suspend fun deleteUserById(id: Int) : Int

    suspend fun deleteAllUser() : Int

    suspend fun getUserById(id: Int) : Users?

    suspend fun checkUserExists(id: Int) : Boolean

    suspend fun updatePatchUser(
        id: Int,
        request: UpdateUserRequest
    ): Int
    suspend fun deleteUserByUsername(username: String): Int

    suspend fun syncDataFromServer()


}