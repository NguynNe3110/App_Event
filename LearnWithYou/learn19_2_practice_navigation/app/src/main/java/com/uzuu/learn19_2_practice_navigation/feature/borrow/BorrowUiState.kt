package com.uzuu.learn19_2_practice_navigation.feature.borrow

import com.uzuu.learn19_2_practice_navigation.domain.model.Item

data class BorrowUiState(
    val items: List<Item> = emptyList(),
    val selectedItem: Item? = null,
    val borrowQty: Int = 1,
    val message: String? = null
)