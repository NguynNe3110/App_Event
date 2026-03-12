package com.uzuu.managerevent.domain.repository

import com.uzuu.managerevent.core.result.ApiResult
import com.uzuu.managerevent.data.remote.dto.BaseResponseDto
import com.uzuu.managerevent.data.remote.dto.response.TokenResponseDto
import com.uzuu.managerevent.domain.model.Login
import com.uzuu.managerevent.domain.model.Register

interface AuthRepository {

    suspend fun registerRequest(request: Register): ApiResult<String>

    suspend fun loginRequest(request: Login): ApiResult<BaseResponseDto<TokenResponseDto>>


}