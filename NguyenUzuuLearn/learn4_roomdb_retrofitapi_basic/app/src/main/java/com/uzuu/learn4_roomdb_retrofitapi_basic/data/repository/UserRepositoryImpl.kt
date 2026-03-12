package com.uzuu.learn4_roomdb_retrofitapi_basic.data.repository

import com.uzuu.learn4_roomdb_retrofitapi_basic.core.result.ApiResult
import com.uzuu.learn4_roomdb_retrofitapi_basic.data.local.dao.UserDao
import com.uzuu.learn4_roomdb_retrofitapi_basic.data.mapper.toDomain
import com.uzuu.learn4_roomdb_retrofitapi_basic.data.mapper.toEntity
import com.uzuu.learn4_roomdb_retrofitapi_basic.data.remote.UserApi
import com.uzuu.learn4_roomdb_retrofitapi_basic.domain.model.User
import com.uzuu.learn4_roomdb_retrofitapi_basic.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException

class UserRepositoryImpl(
    private val api: UserApi,
    private val dao: UserDao
) : UserRepository {

    override val user: Flow<List<User>> =
        dao.observeAll().map { list ->
            list.map {
                it.toDomain()
            }
        }

    override suspend fun refreshUser(): ApiResult<Unit> {
        return try {
            val dtos = api.getUsers()
            //chi co ham extension moi lam được như này
//            val entities = dtos.map { it.toEntity() }

            // map để xử lý 1 list
            val entities = dtos.map { dto ->
                toEntity(dto)
            }

            dao.clearAll()
            dao.insertAll(entities)

            ApiResult.Success(Unit)
        } catch (e: IOException) {
            ApiResult.Error("Mat mang / loi ket noi",e)
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