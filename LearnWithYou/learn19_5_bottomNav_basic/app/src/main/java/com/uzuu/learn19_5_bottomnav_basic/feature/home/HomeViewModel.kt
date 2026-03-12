package com.uzuu.learn19_5_bottomnav_basic.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uzuu.learn19_5_bottomnav_basic.data.repository.ProductRepositoryImpl
import com.uzuu.learn19_5_bottomnav_basic.domain.model.Products
import com.uzuu.learn19_5_bottomnav_basic.domain.repository.ProductRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repo: ProductRepository
): ViewModel() {
    sealed class HomeUiEvent {
        data class Toast(val message: String) : HomeUiEvent()
        object ClearInput : HomeUiEvent()
    }

    data class HomeUiState(
        val idLoading: Boolean = false,
        val error: String ?= null
    )

    private val _homeState = MutableStateFlow(HomeUiState())
    val homeState = _homeState.asStateFlow()

    private val _homeEvent = MutableSharedFlow<HomeUiEvent>(extraBufferCapacity = 3)
    val homeEvent = _homeEvent.asSharedFlow()

    fun onClickAdd(name: String, des: String, price: String) {
        viewModelScope.launch {
            _homeState.update { it.copy(idLoading = true, error = null) }
            delay(400)

            val priceInt = price.toLongOrNull() ?: 0
            val value = repo.insertProduct(
                Products(
                    0,
                    name,
                    priceInt,
                    des
                )
            )
            println("VALUE : value $value")
            if(value > 0){
                _homeState.update { it.copy(idLoading = false, error = null) }
                _homeEvent.tryEmit(HomeUiEvent.ClearInput)
                _homeEvent.tryEmit(HomeUiEvent.Toast("Them thanh cong!"))
                return@launch
            } else {
                _homeState.update { it.copy(idLoading = false, error = "loi roiii") }
                _homeEvent.tryEmit(HomeUiEvent.ClearInput)
                _homeEvent.tryEmit(HomeUiEvent.Toast("Them that bai!"))
                return@launch
            }
        }
    }
}