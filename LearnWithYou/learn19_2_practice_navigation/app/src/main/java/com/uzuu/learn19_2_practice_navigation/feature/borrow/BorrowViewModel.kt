package com.uzuu.learn19_2_practice_navigation.feature.borrow

import androidx.lifecycle.ViewModel
import com.uzuu.learn19_2_practice_navigation.domain.model.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BorrowViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        BorrowUiState(
            items = listOf(
                Item(1, "Laptop", 3),
                Item(2, "Mouse", 10),
                Item(3, "Projector", 2)
            )
        )
    )
    val uiState = _uiState.asStateFlow()

    fun selectItem(itemId: Int) {
        _uiState.update { st ->
            val item = st.items.firstOrNull { it.id == itemId }
            st.copy(
                selectedItem = item,
                borrowQty = 1,
                message = null
            )
        }
    }

    fun incQty() {
        _uiState.update { st ->
            val max = st.selectedItem?.available ?: 0
            val next = (st.borrowQty + 1).coerceAtMost(max.coerceAtLeast(1))
            st.copy(borrowQty = next, message = null)
        }
    }

    fun decQty() {
        _uiState.update { st ->
            val next = (st.borrowQty - 1).coerceAtLeast(1)
            st.copy(borrowQty = next, message = null)
        }
    }

    fun confirmBorrow() {
        _uiState.update { st ->
            val item = st.selectedItem ?: return@update st.copy(message = "No item selected")
            val qty = st.borrowQty
            if (qty <= 0) return@update st.copy(message = "Invalid qty")
            if (qty > item.available) return@update st.copy(message = "Not enough stock")

            val newItems = st.items.map {
                if (it.id == item.id) it.copy(available = it.available - qty) else it
            }
            val newSelected = newItems.first { it.id == item.id }

            st.copy(
                items = newItems,
                selectedItem = newSelected,
                message = "Borrowed $qty ${item.name}"
            )
        }
        return
    }

    fun clearMessage() {
        _uiState.update { it.copy(message = null) }
    }
}
