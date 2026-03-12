package com.uzuu.learn19_5_bottomnav_basic.feature.book.registedCalender.detailRegister

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
import java.text.SimpleDateFormat
import java.util.Locale

class DetailViewModel(
    val repo : TimeRepository
): ViewModel() {
    data class DetailState(
        val times: Times ?= null
    )

    sealed class DetailEvent {
        data class Toast(val mess : String) : DetailEvent()
        object clear: DetailEvent()
        object navigateBack : DetailEvent()
    }

    private val _detailEvent = MutableSharedFlow<DetailEvent>(extraBufferCapacity = 3)
    val detailEvent = _detailEvent.asSharedFlow()

    private val _detailState = MutableStateFlow(DetailState())
    val detailState = _detailState.asStateFlow()

    fun getTimeById(id: Int) {
        viewModelScope.launch {
            val vl = repo.getTimeById(id)
            println("DEBUG in detailVM $vl")
            _detailState.update {
                it.copy(times = vl)
            }
        }
    }

    fun onClickSave(id: Int, start: String, end: String){
        val format = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

        val startDay = format.parse(start)?.time ?: 0
        val endDay = format.parse(end)?.time ?: 0

        viewModelScope.launch {

            val value = repo.updateTime(
                Times(
                    id,startDay, endDay
                )
            )

            if(value > 0) {
                _detailEvent.tryEmit(DetailEvent.Toast("Cap nhat thanh cong!"))
                _detailEvent.tryEmit(DetailEvent.clear)
                _detailEvent.tryEmit(DetailEvent.navigateBack)
            } else {
                _detailEvent.tryEmit(DetailEvent.Toast("cap nhat that bai!"))
            }
        }
    }
}