package com.uzuu.learn12_1_resfulapi_basic.feature.main

sealed class MainUiEvent {
    data class Toast(val message: String) : MainUiEvent()
}