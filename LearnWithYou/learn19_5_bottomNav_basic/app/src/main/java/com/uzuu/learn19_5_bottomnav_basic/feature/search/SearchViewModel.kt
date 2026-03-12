package com.uzuu.learn19_5_bottomnav_basic.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uzuu.learn19_5_bottomnav_basic.data.mapper.toUi
import com.uzuu.learn19_5_bottomnav_basic.domain.model.Products
import com.uzuu.learn19_5_bottomnav_basic.domain.repository.ProductRepository
import com.uzuu.learn19_5_bottomnav_basic.feature.home.HomeViewModel.HomeUiEvent
import com.uzuu.learn19_5_bottomnav_basic.feature.home.HomeViewModel.HomeUiState
import com.uzuu.learn19_5_bottomnav_basic.feature.main.UiItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.publish
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repo: ProductRepository
): ViewModel() {

    sealed class SearchUiEvent {
        data class Toast(val message: String): SearchUiEvent()

        data class NavigateToDetail(val id: Int): SearchUiEvent()
    }

    data class SearchUiState(
        val error: String ?= null,
        val items: List<Products> = emptyList()
    )

    private val _searchState = MutableStateFlow(SearchUiState())
    val searchState = _searchState.asStateFlow()

    private val _searchEvent = MutableSharedFlow<SearchUiEvent>(extraBufferCapacity = 3)
    val searchEvent = _searchEvent.asSharedFlow()

    private val keyword = MutableStateFlow("")

    init {
        observeAll()
    }

    val products = keyword
        .debounce(300)
        .flatMapLatest {
            repo.searchProduct(it)
        }
        .map { list ->
            list.map {
//                UiItem(
//                    id = it.id,
//                    name = it.name,
//                    price = it.price
//                )
                it.toUi()
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun onSearchChange(text: String) {
        keyword.value = text
    }

    fun onClickItem(id: Int) {
        viewModelScope.launch {
            _searchEvent.tryEmit(SearchUiEvent.NavigateToDetail(id))
        }
    }

    fun observeAll() {
        viewModelScope.launch {
            repo.products.collect { list ->
                list.map {
                    _searchState.update {
                        it.copy(items = list)
                    }
                }
            }
        }
    }
}