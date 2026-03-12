package com.uzuu.learn19_4_practice_nav_isolation.feature.listview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uzuu.learn19_4_practice_nav_isolation.domain.repository.ProductRepository
import com.uzuu.learn19_4_practice_nav_isolation.feature.main.UiItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListViewModel(
    private val repository: ProductRepository
): ViewModel() {
    private val _listState = MutableStateFlow(ListUiState())
    val listState = _listState.asStateFlow()

    private val _listEvent = MutableSharedFlow<ListUiEvent>(extraBufferCapacity = 3)
    val listEvent = _listEvent.asSharedFlow()

    init {
        observe()
    }

    fun observe() {
        viewModelScope.launch {
            repository.products.collect { list ->

                val items = list.map {
                    UiItem(it.id, it.name, it.price)
                }

                _listState.update {
                    it.copy(products = items)
                }
            }
        }
    }

    fun onBackList() {
        viewModelScope.launch {
            _listEvent.tryEmit(ListUiEvent.NavigateToHome)
        }
    }

    fun onClickItem(id: Int) {
        viewModelScope.launch {
            _listEvent.tryEmit(ListUiEvent.NavigateToDetail(id))
        }
    }
}