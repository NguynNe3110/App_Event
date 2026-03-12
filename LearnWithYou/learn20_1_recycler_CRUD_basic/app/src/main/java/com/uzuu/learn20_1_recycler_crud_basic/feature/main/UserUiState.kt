package com.uzuu.learn20_1_recycler_crud_basic.feature.main

import com.uzuu.learn20_1_recycler_crud_basic.domain.model.User

data class UserUiState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
    var selectedUser: User ?= null
)
