package com.uzuu.learn19_4_practice_nav_isolation.feature.listview

import com.uzuu.learn19_4_practice_nav_isolation.feature.main.HomeUiEvent

sealed class ListUiEvent {
    data class Toast(val message: String ): ListUiEvent()
    object NavigateToHome: ListUiEvent()
    data class NavigateToDetail(val productId: Int) : ListUiEvent()
}