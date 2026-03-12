package com.uzuu.learn12_1_resfulapi_basic.feature.main

data class MainUiState(
    val isLoading: Boolean = false,
    val status: String = "idle",
    val items: List<UiItem> = emptyList()
)