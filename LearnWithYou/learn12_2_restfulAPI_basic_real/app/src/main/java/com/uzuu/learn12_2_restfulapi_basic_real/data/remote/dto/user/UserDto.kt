package com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.user

data class UserDto(
    val id: Int,
    val username: String,
    val password: String,
    val fullName: String
)