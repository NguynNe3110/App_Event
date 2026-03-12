package com.uzuu.learn12_2_restfulapi_basic_real.feature.main

sealed class UiItem {
    data class UserItem(
        val id: Int,
        val name: String,
        val fullName: String
    ): UiItem()

    data class BookItem(
        val id: Int,
        val name: String,
        val quantity: Int
    ): UiItem()
}