package com.uzuu.learn15_1_roomdb_retrofitapi_basic.feature.main

import com.uzuu.learn15_1_roomdb_retrofitapi_basic.domain.model.User

data class UserUiState(
    val isLoading: Boolean = false,
    val users: List<User> = emptyList(),
    val error: String? = null
)