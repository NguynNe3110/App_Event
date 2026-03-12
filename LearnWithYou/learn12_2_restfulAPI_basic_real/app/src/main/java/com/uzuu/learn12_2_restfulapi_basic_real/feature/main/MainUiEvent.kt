package com.uzuu.learn12_2_restfulapi_basic_real.feature.main

sealed class MainUiEvent {
    data class Toast (val message: String) : MainUiEvent()
}
