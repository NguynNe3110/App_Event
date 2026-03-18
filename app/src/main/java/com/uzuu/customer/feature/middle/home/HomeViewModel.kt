package com.uzuu.customer.feature.middle.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uzuu.customer.domain.repository.EventRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val eventRepo : EventRepository
) : ViewModel() {
    private var currentPage = 0;
    private val _homeState = MutableStateFlow(HomeUiState())
    val homeState = _homeState.asStateFlow()

    private val __homeEvent = MutableSharedFlow<HomeUiEvent>(extraBufferCapacity = 3)
    val homeEvent = __homeEvent.asSharedFlow()

    fun loadEvents() {
        if (_homeState.value.isLoading || _homeState.value.isLastPage) return

        viewModelScope.launch {
            _homeState.update { it.copy(isLoading = true) }

            val result = eventRepo.getEvent(currentPage)

            currentPage++

            _homeState.update {
                it.copy(
                    events = it.events + result.data,
                    isLoading = false,
                    isLastPage = result.isLast
                )
            }
        }
    }
}