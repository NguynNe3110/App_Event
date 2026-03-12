package com.uzuu.learn20_2_recycler_dao.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uzuu.learn20_2_recycler_dao.domain.model.User
import com.uzuu.learn20_2_recycler_dao.domain.repository.UserRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

class MainViewModel(
    private val repo : UserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<MainUiEvent>(extraBufferCapacity = 2)
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        obseverAll()
    }

    fun onUserClick(user: User) {
        _uiState.update { it.copy(userSelected = user) }
    }

    fun FillData(user: User) {
        _uiEvent.tryEmit(
            MainUiEvent.FillData(
                id = user.id,
                name = user.displayName
            )
        )
    }

    fun obseverAll(){
        viewModelScope.launch {
            // listen from db
            repo.users.collect { list ->
                _uiState.update { it.copy(users = list) }
            }
        }
    }

    fun insert(strId: String, strName: String) {
        val id: Int = strId.toIntOrNull() ?: 0
        val name = strName.trim()

        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            delay(300)
            if(repo.checkExists(id)){
                _uiEvent.tryEmit(MainUiEvent.Toast("User da ton tai!"))
                _uiState.update { it.copy(isLoading = false) }
                return@launch
            } else {
                try {
                    repo.insert(User(id, name))

                    _uiState.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                } catch (e: Exception) {
                    _uiEvent.tryEmit(MainUiEvent.Toast("Loi: ${e.message}"))
                }
            }
        }
    }

    fun update(strId: String, strName: String) {
        val id: Int = strId.toIntOrNull() ?: 0
        val name = strName.trim()

        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }

                val result = repo.update(User(id, name))

                _uiState.update { it.copy(isLoading = false) }

                if (result > 0) {
                    _uiEvent.tryEmit(MainUiEvent.Toast("Cập nhật thành công!"))
                    return@launch
                } else _uiEvent.tryEmit(MainUiEvent.Toast("K tìm thấy user!"))
                    return@launch
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
                _uiEvent.tryEmit(MainUiEvent.Toast("Lỗi khi cập nhật!"))
                return@launch
            }
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                delay(300)

                val result = repo.deleteAll()
                _uiState.update { it.copy(isLoading = false) }

                if (result > 0) {
                    _uiEvent.tryEmit(MainUiEvent.Toast("Xóa thành công!"))
                    return@launch
                } else _uiEvent.tryEmit(MainUiEvent.Toast("K tìm thấy user!"))
                    return@launch
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
                _uiEvent.tryEmit(MainUiEvent.Toast("Lỗi khi xóa"))
                return@launch
            }
            _uiEvent.tryEmit(MainUiEvent.Toast("Xoa thanh cong"))
            return@launch
        }
    }

    fun deleteUserById(strId: String) {
        val id: Int = strId.toIntOrNull() ?: 0

        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                delay(300)

                val result = repo.deleteById(id)
                _uiState.update { it.copy(isLoading = false) }

                if (result > 0) {
                    _uiEvent.tryEmit(MainUiEvent.Toast("Xóa thành công!"))
                    return@launch
                } else _uiEvent.tryEmit(MainUiEvent.Toast("K tìm thấy user!"))
                return@launch
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
                _uiEvent.tryEmit(MainUiEvent.Toast("Lỗi khi xóa"))
                return@launch
            }
            _uiEvent.tryEmit(MainUiEvent.Toast("Xoa thanh cong"))
            return@launch
        }
    }

    fun getUserById (id: Int) {
        viewModelScope.launch {
            try {

            } catch (e: Exception) {

            }
        }
    }
}