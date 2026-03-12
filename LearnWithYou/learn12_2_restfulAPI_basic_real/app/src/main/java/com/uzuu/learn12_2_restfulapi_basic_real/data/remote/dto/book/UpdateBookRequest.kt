package com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.book

data class UpdateBookRequest(
    val title: String,
    val quantity: Int,
    //neu server tra ve list thi phai la list
    val categoryId: Int
)

