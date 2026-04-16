package com.uzuu.petrolimex.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uzuu.petrolimex.core.result.ApiResult
import com.uzuu.petrolimex.data.remote.dto.FuelResponse
import com.uzuu.petrolimex.data.repository.WebRepository
import com.uzuu.petrolimex.domain.model.Fuel
import com.uzuu.petrolimex.domain.model.Time
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val webRepo: WebRepository
): ViewModel() {
    private val _uiEvent = MutableSharedFlow<MainUiEvent>(extraBufferCapacity = 5)
    val uiEvent = _uiEvent.asSharedFlow()

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    init {
        observeAll()
    }

    private fun observeAll() {
        viewModelScope.launch {
            combine(
                webRepo.fuels,
                webRepo.time
            ) { fuels, time ->
                println("DEBUG users=${fuels.size}")
                println("DEBUG books=${time}")
                // có thể dùng (fuels to time) bên dưới collect dùng (fuels, time) ->
                FuelResponse(time, fuels)
            }.collect { (time, fuels) ->
                println("DEBUG: items = $time, $fuels")
                _uiState.update {
                    it.copy(
                        fuels = fuels ,
                        time = time
                    )
                }
            }
        }
    }

    fun syncData() {
        println("DEBUG: ViewModel getData called")
        viewModelScope.launch {

            _uiEvent.emit(MainUiEvent.Loading)
            delay(400)

            when(val data = webRepo.syncData()){
                is ApiResult.Success -> {
                    _uiEvent.emit(MainUiEvent.Success)
                    _uiEvent.emit(MainUiEvent.Toast("Đồng bộ thành công!"))
                }

                is ApiResult.Error -> {
                    _uiEvent.emit(MainUiEvent.Error)
                    _uiEvent.emit(MainUiEvent.Toast("Lỗi : ${data.message}"))
                    _uiEvent.emit(MainUiEvent.Toast("Đồng bộ thất bại!"))
                    println("DEBUG: Error = ${data.message}")
                }
            }
        }
    }
}