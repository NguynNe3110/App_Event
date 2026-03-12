package com.uzuu.learn12_1_resfulapi_basic.domain.model

data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)