package com.uzuu.learn12_2_restfulapi_basic_real.domain.model.user

data class CreateUser(
    val username: String,
    val password: String,
    val fullName: String
)
