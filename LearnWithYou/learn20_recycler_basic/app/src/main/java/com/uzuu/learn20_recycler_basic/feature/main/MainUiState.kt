package com.uzuu.learn20_recycler_basic.feature.main

import com.uzuu.learn20_recycler_basic.domain.model.User

data class MainUiState(
    val isLoading: Boolean = false,
    val users:List<User> = emptyList(),
    val error: String ?= null
)
