package com.uzuu.learn20_2_recycler_dao.feature.main

import com.uzuu.learn20_2_recycler_dao.domain.model.User

data class MainUiState(
    val users : List<User> = emptyList(),
    val isLoading: Boolean = false,
    val userSelected : User ?= null
)
