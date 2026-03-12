package com.uzuu.learn15_1_roomdb_retrofitapi_basic.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uzuu.learn15_1_roomdb_retrofitapi_basic.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserViewModel(
    private val repo: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserUiState())
    val uiState = _uiState.asStateFlow()

    init {
        // Luồng hiển thị: DB -> UI
        viewModelScope.launch {
            repo.users.collect { list ->
                _uiState.update { it.copy(users = list) }
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                repo.refreshUsers() // API -> DB
                // Không cần set users ở đây! DB tự phát list mới qua repo.users
                _uiState.update { it.copy(isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message ?: "Unknown error") }
            }
        }
    }
}