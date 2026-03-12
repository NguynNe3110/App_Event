package com.uzuu.managerevent.feature.middle.calender.childcalender.find

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FindViewModel(

): ViewModel() {

    private val _findState = MutableStateFlow(FindUiState())
    val findState = _findState.asStateFlow()

    private val _findEvent = MutableSharedFlow<FindUiEvent>(extraBufferCapacity = 3)
    val findEvent = _findEvent.asSharedFlow()
}