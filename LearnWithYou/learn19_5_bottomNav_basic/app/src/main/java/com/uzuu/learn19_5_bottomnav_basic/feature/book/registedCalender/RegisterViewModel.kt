package com.uzuu.learn19_5_bottomnav_basic.feature.book.registedCalender

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uzuu.learn19_5_bottomnav_basic.domain.model.Times
import com.uzuu.learn19_5_bottomnav_basic.domain.repository.TimeRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class RegisterViewModel(
    private val repo: TimeRepository
): ViewModel() {
    sealed class RegisterUiEvent(){
        data class Toast(val mess: String): RegisterUiEvent()
        data class navigateToDetail(val id: Int) : RegisterUiEvent()
    }

    data class RegisterUiState(
        val itemTimes : List<Times> = emptyList()
    )

    private val _regisEvent = MutableSharedFlow<RegisterUiEvent>(extraBufferCapacity = 3)
    val regisEvent = _regisEvent.asSharedFlow()

    private val _regisState = MutableStateFlow(RegisterUiState())
    val regisState = _regisState.asStateFlow()

    init {
        observe()
    }

    private fun observe() {
        viewModelScope.launch {
            repo.times.collect { list->
                _regisState.update {
                    println("DEBUG list nhan ve tu repo(in viewmodel) $list")
                    it.copy(itemTimes = list)
                }
            }
        }
    }

    fun onClickItem(id: Int) {
        _regisEvent.tryEmit(RegisterUiEvent.navigateToDetail(id))
    }
}