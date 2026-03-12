package com.uzuu.learn12_2_restfulapi_basic_real.domain.model.user

data class UpdateUser(
    val id : Int,
    val username: String?,
    val password: String?,
    val fullName: String?
)
