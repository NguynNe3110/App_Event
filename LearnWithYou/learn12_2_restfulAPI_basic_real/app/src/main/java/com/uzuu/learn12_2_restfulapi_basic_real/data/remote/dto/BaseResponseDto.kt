package com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto

data class BaseResponseDto<T>(
    val code: Int,
    val message: String,
    val result: T
)
