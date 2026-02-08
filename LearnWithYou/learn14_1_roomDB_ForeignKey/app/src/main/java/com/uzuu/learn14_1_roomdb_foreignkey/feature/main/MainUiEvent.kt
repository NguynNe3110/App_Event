package com.uzuu.learn14_1_roomdb_foreignkey.feature.main

sealed class MainUiEvent {
    data class Toast(val message: String) : MainUiEvent()
}