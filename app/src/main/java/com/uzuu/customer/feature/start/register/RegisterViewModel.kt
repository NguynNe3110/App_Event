package com.uzuu.customer.feature.start.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uzuu.customer.core.result.ApiResult
import com.uzuu.customer.domain.model.Register
import com.uzuu.customer.domain.repository.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authRepo: AuthRepository
):  ViewModel() {
    private val _registerState = MutableStateFlow(RegisterUiState())
    val registerState = _registerState.asStateFlow()

    private val _registerEvent = MutableSharedFlow<RegisterUiEvent>()
    val registerEvent = _registerEvent.asSharedFlow()

    fun onClickRegister(
        user: String,
        pass: String,
        email: String,
        disp: String,
        phone: String,
        add: String
    ) {
        viewModelScope.launch {
            _registerState.update { it.copy(isLoading = true) }
            _registerEvent.tryEmit(RegisterUiEvent.Loading)
            delay(400)

            if (user.isBlank() || pass.isBlank() || email.isBlank() || disp.isBlank() || phone.isBlank() || add.isBlank()) {
                _registerEvent.tryEmit(RegisterUiEvent.Toast("Không được để trống"))
                _registerEvent.tryEmit(RegisterUiEvent.Error)
                return@launch
            }

            val regis = Register(user, pass, email, disp, phone, add)
            when(val r = authRepo.registerRequest(regis)) {
                is ApiResult.Success -> {
                    _registerState.update { it.copy(isLoading = false) }
                    _registerEvent.tryEmit(RegisterUiEvent.Success)
                    _registerEvent.tryEmit(RegisterUiEvent.Toast("Đăng kí thành công!"))
                    _registerEvent.tryEmit(RegisterUiEvent.Toast("Tự động chuyển hướng sau 1 giây!"))
                    delay(300)
                    _registerEvent.tryEmit(RegisterUiEvent.navigateToLogin(user))
                }
                is ApiResult.Error -> {
                    _registerState.update { it.copy(isLoading = false) }
                    _registerEvent.tryEmit(RegisterUiEvent.Error)
                    _registerEvent.tryEmit(RegisterUiEvent.Toast(r.message))
                }
            }
        }
    }
}