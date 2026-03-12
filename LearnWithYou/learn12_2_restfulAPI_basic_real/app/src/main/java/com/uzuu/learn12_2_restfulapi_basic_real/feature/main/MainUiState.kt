package com.uzuu.learn12_2_restfulapi_basic_real.feature.main

import com.uzuu.learn12_2_restfulapi_basic_real.domain.model.book.Categories

data class MainUiState(
    val isLoading : Boolean = false,
    val error: String ? = null,
    val items: List<UiItem> = emptyList(),
    val categories : List<Categories> = emptyList()
)