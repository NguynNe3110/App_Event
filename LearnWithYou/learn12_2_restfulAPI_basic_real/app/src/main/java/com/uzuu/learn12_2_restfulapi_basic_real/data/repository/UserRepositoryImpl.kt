package com.uzuu.learn12_2_restfulapi_basic_real.data.repository

import com.uzuu.learn12_2_restfulapi_basic_real.core.result.ApiResult
import com.uzuu.learn12_2_restfulapi_basic_real.data.local.datasource.UserLocalDataSource
import com.uzuu.learn12_2_restfulapi_basic_real.data.local.entity.UsersEntity
import com.uzuu.learn12_2_restfulapi_basic_real.data.mapper.entityToDomain
import com.uzuu.learn12_2_restfulapi_basic_real.data.mapper.userDtoToEntity
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.datasource.UserRemoteDataSource
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.BaseResponseDto
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.user.CreateUserRequest
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.user.UpdateUserRequest
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.user.UserDto
import com.uzuu.learn12_2_restfulapi_basic_real.domain.model.user.Users
import com.uzuu.learn12_2_restfulapi_basic_real.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException

class UserRepositoryImpl(
    private val local: UserLocalDataSource,
    private val remote: UserRemoteDataSource
) : UserRepository {

    override val users: Flow<List<Users>> =
        local.observeUsers().map { listEntity ->
            listEntity.map { it.entityToDomain() }
        }

    // dung Toast
    override suspend fun getUserById(id: Int): Users? {
        return local.getUserById(id)?.entityToDomain()
    }

    override suspend fun checkUserExists(id: Int): Boolean {
        return local.checkUserExists(id)
    }

    override suspend fun createUser(request: CreateUserRequest): Long {
        val userDto = remote.createUser(request).result
        return local.createUser(userDto.userDtoToEntity())
    }

    override suspend fun deleteAllUser(): Int {
        return local.deleteAllUser()
    }

    override suspend fun deleteUserById(id: Int): Int {
        return local.deleteUserById(id)
    }

    override suspend fun deleteUserByUsername(username: String): Int {
        remote.deleteUser(username)
        return local.deleteUserByUsername(username)
    }


    override suspend fun updatePutUser(
        username: String,
        request: UpdateUserRequest
    ): Int {
        val userDto = remote.updateFullUser(username, request).result
        return local.updateUser(userDto.userDtoToEntity())
    }

    // sua 1 phan (check null neu thieu thi dung)
    override suspend fun updatePatchUser(
        id: Int,
        request: UpdateUserRequest
    ): Int {
        val userDto = remote.updateUser(id , request).result
        return local.updateUser(userDto.userDtoToEntity())
    }

    // dành cho sysnc dữ lieu
    override suspend fun syncDataFromServer() {
        val users = getAllUsersApi()
        local.deleteAllUser()
        return local.InsertAllUser(users)
    }

    suspend fun getAllUsersApi(): List<UsersEntity> {
        val users = remote.getAllUsers()
            .result
            .map { it.userDtoToEntity() }
        return users
    }

    private suspend fun <T> safeCall(block: suspend () -> T): ApiResult<T> {
        //    block là một biến
//    Kiểu của nó là: một hàm
//    Không nhận tham số
//    Trả về kiểu T
//    Là suspend function
        return try {
            ApiResult.Success(block())
        } catch (e: IOException) {
            ApiResult.Error("Network error (mất mạng/timeout)", e)
        } catch (e: HttpException) {
            ApiResult.Error("HTTP ${e.code()} ${e.message()}", e)
        } catch (e: Exception) {
            ApiResult.Error("Unknown error: ${e.message}", e)
        }
    }
}