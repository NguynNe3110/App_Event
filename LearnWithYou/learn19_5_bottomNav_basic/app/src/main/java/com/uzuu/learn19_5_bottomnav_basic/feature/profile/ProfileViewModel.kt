package com.uzuu.learn19_5_bottomnav_basic.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uzuu.learn19_5_bottomnav_basic.data.mapper.toUi
import com.uzuu.learn19_5_bottomnav_basic.domain.model.Products
import com.uzuu.learn19_5_bottomnav_basic.domain.repository.ProductRepository
import com.uzuu.learn19_5_bottomnav_basic.feature.home.HomeViewModel.HomeUiEvent
import com.uzuu.learn19_5_bottomnav_basic.feature.home.HomeViewModel.HomeUiState
import com.uzuu.learn19_5_bottomnav_basic.feature.main.UiItem
import com.uzuu.learn19_5_bottomnav_basic.feature.search.SearchViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repo: ProductRepository
): ViewModel() {

    sealed class ProfileUiEvent {
        data class Toast(val message: String): ProfileUiEvent()

        object NavigateBack: ProfileUiEvent()
    }

    data class ProfileUiState(
        val error: String ?= null,
        val items: Products ?= null
    )

    private val _profileState = MutableStateFlow(ProfileUiState())
    val profileState = _profileState.asStateFlow()

    private val _profileEvent = MutableSharedFlow<ProfileUiEvent>(extraBufferCapacity = 3)
    val profileEvent = _profileEvent.asSharedFlow()

    fun getProduct(id: Int) {
        viewModelScope.launch {
            val pro = repo.getProductById(id)
            _profileState.update {
                it.copy(
                    items = pro
                )
            }
        }
    }

    fun onClickDelete(id: Int) {
        viewModelScope.launch {
            repo.deleteProductById(id)
            _profileEvent.tryEmit(ProfileUiEvent.Toast("Xoa thanh cong!"))
            _profileEvent.tryEmit(ProfileUiEvent.NavigateBack)
        }
    }

    fun onClickUpdate(id: Int, name: String, pri: String, des: String) {

        val price = pri.toLongOrNull() ?: 0
        viewModelScope.launch {

            val value = repo.updateProduct(
                Products(id, name, price,des)
            )

            if(value > 0) {
                _profileEvent.tryEmit(ProfileUiEvent.Toast("Update thanh cong!"))
                _profileEvent.tryEmit(ProfileUiEvent.NavigateBack)
                return@launch
            } else {
                _profileEvent.tryEmit(ProfileUiEvent.Toast("Update that bai!"))
                return@launch
            }
        }
    }
}