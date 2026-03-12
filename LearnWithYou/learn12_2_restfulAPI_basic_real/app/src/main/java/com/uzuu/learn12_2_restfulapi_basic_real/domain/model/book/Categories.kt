package com.uzuu.learn12_2_restfulapi_basic_real.domain.model.book

data class Categories(
    val id: Int,
    val categoriesName: String
) {
    override fun toString(): String {
        return categoriesName
    }
}