package com.uzuu.learn4_roomdb_retrofitapi_basic.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewModelScope
import com.uzuu.learn4_roomdb_retrofitapi_basic.core.result.ApiResult
import com.uzuu.learn4_roomdb_retrofitapi_basic.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MainViewModel(
    private val repo: UserRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadUser()
    }

    fun loadUser(){
        viewModelScope.launch {
            repo.user.collect { list ->
                _uiState.update { it.copy(users = list) }
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            when (val result = repo.refreshUser()){
                is ApiResult.Success -> {
                    _uiState.update { it.copy(isLoading = false) }
                }
                is ApiResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = result.message ?: "Unknown Error") }
                }
            }
        }
    }

    fun deleteAll(){
        viewModelScope.launch { repo.deleteAll() }
    }
}

