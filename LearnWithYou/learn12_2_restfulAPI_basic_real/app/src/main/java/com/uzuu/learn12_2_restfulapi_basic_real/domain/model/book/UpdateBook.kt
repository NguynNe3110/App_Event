package com.uzuu.learn12_2_restfulapi_basic_real.domain.model.book

data class UpdateBook(
    val id: Int,
    val bookName: String?,
    val quantity: Int?,
    val categoriesId: Int?
)
