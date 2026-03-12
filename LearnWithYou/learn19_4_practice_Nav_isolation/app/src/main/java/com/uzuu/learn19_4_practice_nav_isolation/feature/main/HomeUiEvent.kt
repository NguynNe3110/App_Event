package com.uzuu.learn19_4_practice_nav_isolation.feature.main

sealed class HomeUiEvent {
    data class Toast(val message: String) : HomeUiEvent()
    object NavigateToList: HomeUiEvent()

    object clearInput : HomeUiEvent()
}