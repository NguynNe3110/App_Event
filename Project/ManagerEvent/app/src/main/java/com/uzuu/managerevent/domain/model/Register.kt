package com.uzuu.managerevent.domain.model

data class Register (
    val username: String,
    val password: String,
    val email: String,
    val fullName: String,
    val phone: String,
    val address: String
)