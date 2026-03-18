package com.uzuu.customer.feature.middle.home

import com.uzuu.customer.domain.model.Event

data class HomeUiState (
    val isLoading : Boolean = false,
    val isLastPage : Boolean = false,
    val items : List<UiItemHome> = emptyList(),
    val events: List<Event> = emptyList()
)