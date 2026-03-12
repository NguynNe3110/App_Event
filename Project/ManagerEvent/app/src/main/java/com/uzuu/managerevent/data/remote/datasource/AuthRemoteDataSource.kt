package com.uzuu.managerevent.data.remote.datasource

import com.uzuu.managerevent.core.result.ApiResult
import com.uzuu.managerevent.data.remote.api.AuthApi
import com.uzuu.managerevent.data.remote.dto.BaseResponseDto
import com.uzuu.managerevent.data.remote.dto.request.LoginRequestDto
import com.uzuu.managerevent.data.remote.dto.request.RegisterRequestDto
import com.uzuu.managerevent.data.remote.dto.response.TokenResponseDto

class AuthRemoteDataSource(
    private val authApi: AuthApi
) {

    suspend fun register(request: RegisterRequestDto): BaseResponseDto<String> {
        return authApi.register(request)
    }

    suspend fun login(request: LoginRequestDto): BaseResponseDto<TokenResponseDto> {
        return authApi.login(request)
    }
}