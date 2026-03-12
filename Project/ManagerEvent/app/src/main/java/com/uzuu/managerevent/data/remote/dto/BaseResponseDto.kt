package com.uzuu.managerevent.data.remote.dto

data class BaseResponseDto<T>(
    val code: Int,
    val message: String,
    val result: T
)
