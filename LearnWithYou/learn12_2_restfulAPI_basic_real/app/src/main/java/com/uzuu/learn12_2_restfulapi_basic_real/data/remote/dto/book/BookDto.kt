package com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.book

import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.CategoryDto

data class BookDto(
    val id: Int,
    val title: String,
    val quantity: Int,
    //neu server tra ve list thi phai la list
    val category: CategoryDto
)