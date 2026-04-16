package com.uzuu.petrolimex.feature.main

sealed class MainUiEvent {
    data class Toast(val message: String) : MainUiEvent()
    object Loading : MainUiEvent()
    object Success  : MainUiEvent()
    object Error : MainUiEvent()
}