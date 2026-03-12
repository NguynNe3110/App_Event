package com.uzuu.learn20_2_recycler_dao.feature.main

sealed class MainUiEvent {
    data class Toast (val message: String): MainUiEvent()
    data class FillData(
        val id: Int,
        val name: String
    ) : MainUiEvent()
}
