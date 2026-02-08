package com.uzuu.learn3_roomdb_basic.feature.main

import com.uzuu.learn3_roomdb_basic.domain.model.User

data class MainUiState(
    val users: List<User> = emptyList(),
    val isLoading : Boolean = false,
    val error: String? = null
)