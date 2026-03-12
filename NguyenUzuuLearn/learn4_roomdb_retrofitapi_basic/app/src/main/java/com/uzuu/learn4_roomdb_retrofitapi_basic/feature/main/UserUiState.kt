package com.uzuu.learn4_roomdb_retrofitapi_basic.feature.main

import com.uzuu.learn4_roomdb_retrofitapi_basic.domain.model.User

data class UserUiState (
    val error: String ?= null,
    val isLoading: Boolean = false,
    val users : List<User> = emptyList()
)