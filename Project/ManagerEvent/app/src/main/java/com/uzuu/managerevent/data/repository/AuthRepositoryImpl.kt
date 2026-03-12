package com.uzuu.managerevent.data.repository

import com.uzuu.managerevent.core.result.ApiResult
import com.uzuu.managerevent.data.mapper.loginDomainToDto
import com.uzuu.managerevent.data.mapper.registerDomainToDto
import com.uzuu.managerevent.data.remote.datasource.AuthRemoteDataSource
import com.uzuu.managerevent.data.remote.dto.BaseResponseDto
import com.uzuu.managerevent.data.remote.dto.response.TokenResponseDto
import com.uzuu.managerevent.domain.model.Login
import com.uzuu.managerevent.domain.model.Register
import com.uzuu.managerevent.domain.repository.AuthRepository
import retrofit2.HttpException
import java.io.IOException

class AuthRepositoryImpl(
    private val remote: AuthRemoteDataSource
): AuthRepository {
    override suspend fun registerRequest(request: Register): ApiResult<String> =
        safeCall {
            val response = remote.register(request.registerDomainToDto())

            if (response.code == 200) {
                response.result
            } else {
                throw Exception(response.message)
            }
        }

    override suspend fun loginRequest(request: Login): ApiResult<BaseResponseDto<TokenResponseDto>> =
        safeCall {
            remote.login(request.loginDomainToDto())
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