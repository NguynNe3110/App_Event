package com.uzuu.learn14_roomdb.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uzuu.learn14_roomdb.data.UserRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val repo: UserRepository
) : ViewModel() {
    private val _uiEvent = MutableSharedFlow<MainUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    //hàm init là hàm tự chạy ngay sau khi viewmodel được khởi tạo
    init {
        observeUsers()
    }

    private fun observeUsers() {
        viewModelScope.launch {
            repo.users
                .catch { e ->
                    _uiState.update { it.copy(isLoading = false, error = e.message) }
                }
                .collect { list ->
                    _uiState.update { it.copy(users = list, isLoading = false, error = null) }
                }
        }
    }

    fun onSeedClick() {
        viewModelScope.launch {
            repo.seedSample()
        _uiEvent.tryEmit(MainUiEvent.Toast("Ceed thành công!"))
        }
    }

    fun onClearClick() {
        viewModelScope.launch {
            repo.clearAll()
        _uiEvent.tryEmit(MainUiEvent.Toast("Clear thành công!"))
        }
    }
}
