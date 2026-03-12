package com.uzuu.managerevent.feature.middle.home

import androidx.lifecycle.ViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.uzuu.managerevent.domain.repository.EventRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val eventRepo : EventRepository
) : ViewModel() {
    private val _homeState = MutableStateFlow(HomeUiState())
    val homeState = _homeState.asStateFlow()

    private val __homeEvent = MutableSharedFlow<HomeUiEvent>(extraBufferCapacity = 3)
    val homeEvent = __homeEvent.asSharedFlow()


    fun onClickCreate() {
        datePicker()
    }

    fun datePicker() {

    }


}