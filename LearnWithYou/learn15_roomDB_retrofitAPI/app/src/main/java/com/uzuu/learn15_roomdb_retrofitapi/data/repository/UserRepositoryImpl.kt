package com.uzuu.learn15_roomdb_retrofitapi.data.repository

import com.uzuu.learn15_roomdb_retrofitapi.core.result.ApiResult
import com.uzuu.learn15_roomdb_retrofitapi.data.local.dao.UserDao
import com.uzuu.learn15_roomdb_retrofitapi.data.mapper.toDomain
import com.uzuu.learn15_roomdb_retrofitapi.data.mapper.toEntity
import com.uzuu.learn15_roomdb_retrofitapi.data.remote.UserApi
import com.uzuu.learn15_roomdb_retrofitapi.domain.model.User
import com.uzuu.learn15_roomdb_retrofitapi.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException

class UserRepositoryImpl(
    private val api: UserApi,
    private val dao: UserDao
) : UserRepository {

    override val users: Flow<List<User>> =
        dao.observeAll().map { list ->
            list.map { it.toDomain() }
        }

    override suspend fun refreshUsers(): ApiResult<Unit> {
        return try {
            val dtos = api.getUsers()
            val entities = dtos.map { it.toEntity() }

            dao.clearAll()
            dao.insertAll(entities)

            ApiResult.Success(Unit)
        } catch (e: IOException) {
            ApiResult.Error("Mất mạng / lỗi kết nối", e)
        } catch (e: HttpException) {
            ApiResult.Error("HTTP ${e.code()} - ${e.message()}", e)
        } catch (e: Exception) {
            ApiResult.Error("Lỗi không xác định: ${e.message}", e)
        }
    }

    override suspend fun deleteAll() {
        dao.clearAll()
    }
}