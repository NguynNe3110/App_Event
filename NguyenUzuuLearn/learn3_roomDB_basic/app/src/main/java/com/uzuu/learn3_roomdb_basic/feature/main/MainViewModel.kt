package com.uzuu.learn3_roomdb_basic.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uzuu.learn3_roomdb_basic.data.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

class MainViewModel(
    private val repo: UserRepository
) : ViewModel(){

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    init {
        observeUsers()
    }

    private fun observeUsers() {
        viewModelScope.launch {
            repo.users
                .catch { e->
                    _uiState.update { it.copy(isLoading = true, error = e.message) }
                }
                .collect { list ->
                    _uiState.update { it.copy(users = list, isLoading = false, error = null) }
                }
        }
    }

    fun onSeedClick() {
        viewModelScope.launch {
            repo.seedSample()
        }
    }

    fun onClearClick() {
        viewModelScope.launch {
            repo.clearAll()
        }
    }

    fun onInsertClick(id: Int, name: String) {
        viewModelScope.launch {
            repo.insertOne(id, name)
        }
    }
}