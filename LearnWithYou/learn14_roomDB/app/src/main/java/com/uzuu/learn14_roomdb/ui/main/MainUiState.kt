package com.uzuu.learn14_roomdb.ui.main

import com.uzuu.learn14_roomdb.domain.User

data class MainUiState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)
