package com.uzuu.learn12_1_resfulapi_basic.data.remote.dto

data class CommentDto(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
)