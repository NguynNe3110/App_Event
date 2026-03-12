package com.uzuu.managerevent.data.remote.dto.request

data class UserRequest (
    val password: String,
    val email: String,
    val fullName: String,
    val phone: String,
    val address: String
)