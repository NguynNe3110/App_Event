package com.uzuu.learn19_5_bottomnav_basic.feature.book.findCalender

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uzuu.learn19_5_bottomnav_basic.domain.model.Times
import com.uzuu.learn19_5_bottomnav_basic.domain.repository.TimeRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale


class FindViewModel(
    private val repo: TimeRepository
): ViewModel() {
    sealed class FindUiEvent(){
        data class Toast(val mess: String): FindUiEvent()
        object clear: FindUiEvent()
    }

    data class FindUiState(
        val dateTimeStart : Long ?= null,
        val dateTimeEnd: Long ?= null,
        val error: String ?= null
    )

    private val _findEvent = MutableSharedFlow<FindUiEvent>(extraBufferCapacity = 3)
    val findEvent = _findEvent.asSharedFlow()

    private val _findState = MutableStateFlow(FindUiState())
    val findState = _findState.asStateFlow()

    fun setStartDateTime(timestamp: Long) {
        _findState.value = _findState.value.copy(
            dateTimeStart = timestamp
        )
    }

    fun setEndDateTime(timestamp: Long) {
        _findState.value = _findState.value.copy(
            dateTimeEnd = timestamp
        )
    }

    fun onClickSave(start: String, end: String) {
        println("DEBUG nhan vao in findVM $start & $end")
        val format = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

        val startDay = format.parse(start)?.time ?: 0
        val endDay = format.parse(end)?.time ?: 0

        println("DEBUG nhan vao in findVM $startDay & $endDay")

        viewModelScope.launch {

            val value = repo.insertTime(
                Times(
                0,startDay, endDay
                )
            )

            if(value > 0) {
                _findEvent.tryEmit(FindUiEvent.Toast("them thanh cong!"))
                _findEvent.tryEmit(FindUiEvent.clear)
            } else {
                _findEvent.tryEmit(FindUiEvent.Toast("them that bai!"))
            }
        }
    }
}