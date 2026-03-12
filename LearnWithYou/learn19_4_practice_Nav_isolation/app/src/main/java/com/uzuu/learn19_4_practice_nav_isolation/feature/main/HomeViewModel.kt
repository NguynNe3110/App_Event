package com.uzuu.learn19_4_practice_nav_isolation.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uzuu.learn19_4_practice_nav_isolation.domain.model.Products
import com.uzuu.learn19_4_practice_nav_isolation.domain.repository.ProductRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val productRepo: ProductRepository
): ViewModel(){
    private val _homeState = MutableStateFlow(HomeUiState())
    val homeState = _homeState.asStateFlow()

    private val _homeEvent = MutableSharedFlow<HomeUiEvent>(extraBufferCapacity = 3)
    val homeEvent = _homeEvent.asSharedFlow()


    fun onAdditional(name: String, des: String, price: Long){
        viewModelScope.launch {
            _homeState.update { it.copy(isLoading = true, error = null) }

            val value = productRepo.insertProduct(
                Products(0, name, price, des)
            )

            if(value > 0){
                _homeState.update {
                    it.copy(
                        isLoading = false
                    )
                }
                _homeEvent.tryEmit(HomeUiEvent.clearInput)
                _homeEvent.tryEmit(HomeUiEvent.Toast("Them thanh cong!"))
                return@launch
            } else {
                _homeState.update { it.copy(isLoading = false, error = "Loi roi") }
                _homeEvent.tryEmit(HomeUiEvent.Toast("Loi roiii!"))
                return@launch
            }
        }

        viewModelScope.launch {

        }
    }

    fun onClickViewList() {
        _homeEvent.tryEmit(HomeUiEvent.NavigateToList)
    }
}