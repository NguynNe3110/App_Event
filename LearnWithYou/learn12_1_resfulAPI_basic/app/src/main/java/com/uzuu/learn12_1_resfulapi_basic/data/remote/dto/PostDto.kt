package com.uzuu.learn12_1_resfulapi_basic.data.remote.dto

data class PostDto(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)