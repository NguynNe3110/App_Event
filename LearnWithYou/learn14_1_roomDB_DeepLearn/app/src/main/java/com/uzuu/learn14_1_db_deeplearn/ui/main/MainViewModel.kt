package com.uzuu.learn14_1_db_deeplearn.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uzuu.learn14_1_db_deeplearn.data.repository.DbRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val repo: DbRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    init {
        observeUsers()
    }

    private fun observeUsers() {
        viewModelScope.launch {
            repo.observeUsers().collect { list->
                val text = if(list.isEmpty()) {
                    "no users"
                } else {
                    list.joinToString("\n") { u->
                        "id = ${u.id} | name = ${u.name} | email = ${u.email}"

                    }
                }
                _uiState.update { it.copy(usersText = text) }
                //_uiState.value =  _uiState.value.copy(usersText = text)
            }
        }
    }
    fun addUser(name: String, email: String) {
        viewModelScope.launch {
            val id = repo.addUser(name, email)
            pushLog("✅ Insert User: id=$id, name=$name, email=$email")
        }
    }

    fun addPost(userId: Long, title: String) {
        viewModelScope.launch {
            runCatching {
                val id = repo.addPost(userId, title)
                pushLog("✅ Insert Post: id=$id, userId=$userId, title=$title")
            }.onFailure { e ->
                pushLog("❌ Insert Post FAILED: ${e.message}")
            }
        }
    }

    fun updateUser(id: Long, name: String, email: String) {
        viewModelScope.launch {
            val rows = repo.updateUser(id, name, email)
            pushLog("🛠 Update User rows=$rows (id=$id)")
        }
    }

    fun deleteUser(id: Long) {
        viewModelScope.launch {
            val rows = repo.deleteUserById(id)
            pushLog("🗑 Delete User rows=$rows (id=$id) - Posts sẽ bị CASCADE")
        }
    }

    fun clearAll() {
        viewModelScope.launch {
            repo.clearAll()
            pushLog("🧹 Clear ALL")
        }
    }

    private fun pushLog(line: String) {
        val current = _uiState.value.logText
        _uiState.value = _uiState.value.copy(
            logText = if (current.isBlank()) line else "$line\n$current"
        )
    }
}