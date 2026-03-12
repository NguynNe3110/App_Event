package com.uzuu.learn20_1_recycler_crud_basic.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uzuu.learn20_1_recycler_crud_basic.domain.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UserUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UserUiEvent>(extraBufferCapacity = 8)
    val uiEvent = _uiEvent.asSharedFlow()

    fun onUserClick(user: User){
        _uiState.update {it.copy(selectedUser = user)}
        _uiEvent.tryEmit(UserUiEvent.FillInputs(code = user.id, name = user.displayName))
    }

    fun onAdd(code: String, name: String) {
        val id = code.trim()
        val displayName = name.trim()

        if (id.isEmpty() || displayName.isEmpty()) {
            _uiEvent.tryEmit(UserUiEvent.Toast("Vui lòng nhập đủ mã và tên"))
            return
        }

        viewModelScope.launch {
            setLoading(true)
            delay(300)

            val current = _uiState.value.users
            if (current.any { it.id == id }) {
                setLoading(false)
                _uiEvent.tryEmit(UserUiEvent.Toast("Mã '$id' đã tồn tại"))
                return@launch
            }

            val newList = current + User(id = id, displayName = displayName)
            _uiState.update { it.copy(users = newList, selectedUser = null) }

            setLoading(false)
            _uiEvent.tryEmit(UserUiEvent.Toast("Đã thêm: $id"))
        }
    }

    fun onUpdate(code: String, name: String) {
        val selected = _uiState.value.selectedUser
        if (selected == null) {
            _uiEvent.tryEmit(UserUiEvent.Toast("Hãy chọn 1 dòng trong danh sách trước"))
            return
        }

        val newId = code.trim()
        val newName = name.trim()

        if (newId.isEmpty() || newName.isEmpty()) {
            _uiEvent.tryEmit(UserUiEvent.Toast("Vui lòng nhập đủ mã và tên"))
            return
        }

        viewModelScope.launch {
            setLoading(true)
            delay(600)

            val current = _uiState.value.users

            // đổi mã nhưng bị trùng với mã khác
            val conflict = current.any { it.id == newId && it.id != selected.id }
            if (conflict) {
                setLoading(false)
                _uiEvent.tryEmit(UserUiEvent.Toast("Không thể đổi mã sang '$newId' vì đã tồn tại"))
                return@launch
            }

            val newList = current.map {
                if (it.id == selected.id) User(id = newId, displayName = newName) else it
            }

            _uiState.update {
                it.copy(
                    users = newList,
                    selectedUser = User(id = newId, displayName = newName)
                )
            }

            setLoading(false)
            _uiEvent.tryEmit(UserUiEvent.Toast("Đã sửa: ${selected.id} → $newId"))
        }
    }

    fun onDelete() {
        val selected = _uiState.value.selectedUser
        if (selected == null) {
            _uiEvent.tryEmit(UserUiEvent.Toast("Hãy chọn 1 dòng trong danh sách trước"))
            return
        }

        viewModelScope.launch {
            setLoading(true)
            delay(600)

            val current = _uiState.value.users
            val newList = current.filterNot { it.id == selected.id }

            _uiState.update { it.copy(users = newList, selectedUser = null) }

            setLoading(false)
            _uiEvent.tryEmit(UserUiEvent.ClearInputs)
            _uiEvent.tryEmit(UserUiEvent.Toast("Đã xóa: ${selected.id}"))
        }
    }

    fun onClear() {
        viewModelScope.launch {
            setLoading(true)
            delay(300)

            _uiState.update { it.copy(selectedUser = null) }

            setLoading(false)
            _uiEvent.tryEmit(UserUiEvent.ClearInputs)
            _uiEvent.tryEmit(UserUiEvent.Toast("Đã clear"))
        }
    }

    private fun setLoading(value: Boolean) {
        _uiState.update { it.copy(isLoading = value) }
    }
}
