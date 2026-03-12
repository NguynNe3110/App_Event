package com.uzuu.learn20_1_recycler_crud_basic.feature.main

sealed class UserUiEvent {
    data class Toast(val message: String) : UserUiEvent()
    data class FillInputs(
        val code: String,
        val name: String
    ) : UserUiEvent()
    object ClearInputs: UserUiEvent()
}