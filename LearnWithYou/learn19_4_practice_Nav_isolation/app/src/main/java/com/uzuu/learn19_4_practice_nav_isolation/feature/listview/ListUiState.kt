package com.uzuu.learn19_4_practice_nav_isolation.feature.listview

import com.uzuu.learn19_4_practice_nav_isolation.domain.model.Products
import com.uzuu.learn19_4_practice_nav_isolation.feature.main.UiItem

data class ListUiState (
    val products : List<UiItem> = emptyList(),
)