package com.uzuu.learn15_roomdb_retrofitapi.feature.main

import com.uzuu.learn15_roomdb_retrofitapi.domain.model.User

data class UserUiState(
    val isLoading: Boolean = false,
    val users: List<User> = emptyList(),
    val error: String? = null
)