package com.uzuu.learn19_4_practice_nav_isolation.feature.detail

sealed class DetailUiEvent {
    data class Toast(val message: String ): DetailUiEvent()

    object onPopStack : DetailUiEvent()

    object opPopToHome: DetailUiEvent()
}