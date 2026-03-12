package com.uzuu.managerevent.feature.middle.home

data class HomeUiState (
    val isLoading : Boolean = false,
    val items : List<UiItemHome> = emptyList()
)