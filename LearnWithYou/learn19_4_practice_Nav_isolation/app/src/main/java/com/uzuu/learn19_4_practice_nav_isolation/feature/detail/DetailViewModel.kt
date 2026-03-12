package com.uzuu.learn19_4_practice_nav_isolation.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uzuu.learn19_4_practice_nav_isolation.domain.model.Products
import com.uzuu.learn19_4_practice_nav_isolation.domain.repository.ProductRepository
import com.uzuu.learn19_4_practice_nav_isolation.feature.main.HomeUiEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: ProductRepository
): ViewModel(){
    private val _detailState = MutableStateFlow(DetailUiState())
    val detailState = _detailState.asStateFlow()

    private val _detailEvent = MutableSharedFlow<DetailUiEvent>(extraBufferCapacity = 3)
    val detailEvent = _detailEvent.asSharedFlow()

    fun onUpdate(pro: Products) {
        viewModelScope.launch {
            val value = repository.updateProduct(pro)

            if (value > 0) {
                _detailEvent.tryEmit(DetailUiEvent.Toast("Sua thanh cong!"))
                _detailEvent.tryEmit(DetailUiEvent.onPopStack)
                return@launch
            } else {
                _detailEvent.tryEmit(DetailUiEvent.Toast("Loi roiii!"))
                return@launch
            }
        }
    }

    fun onDelete(id: Int) {
        viewModelScope.launch {
            val value = repository.deleteProductById(id)
            if (value > 0) {
                _detailEvent.tryEmit(DetailUiEvent.Toast("Xoa thanh cong!"))
                _detailEvent.tryEmit(DetailUiEvent.onPopStack)
                return@launch
            } else {
                _detailEvent.tryEmit(DetailUiEvent.Toast("Loi roiii!"))
                return@launch
            }
        }
    }

    fun loadProduct(id: Int) {
        viewModelScope.launch {
            val product = repository.getProductById(id)

            _detailState.update {
                it.copy(product = product)
            }
        }
    }

    fun onBackStack() {
        _detailEvent.tryEmit(DetailUiEvent.onPopStack)
    }

    fun onBackHome() {
        _detailEvent.tryEmit(DetailUiEvent.opPopToHome)
    }
}