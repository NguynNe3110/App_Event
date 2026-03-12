package com.uzuu.learn12_1_resfulapi_basic.domain.model

data class Comment(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
)