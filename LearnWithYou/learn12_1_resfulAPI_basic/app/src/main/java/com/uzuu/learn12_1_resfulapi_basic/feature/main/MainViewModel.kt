package com.uzuu.learn12_1_resfulapi_basic.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uzuu.learn12_1_resfulapi_basic.core.result.ApiResult
import com.uzuu.learn12_1_resfulapi_basic.domain.repository.MainRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val repo: MainRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<MainUiEvent>(extraBufferCapacity = 1)
    val uiEvent = _uiEvent.asSharedFlow()

    //
    private var lastPostsCache: MutableList<UiItem.PostItem> = mutableListOf()

    fun getPosts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, status = "Loading posts...", items = emptyList()) }

            when (val r = repo.getPosts()) {
                is ApiResult.Success -> {
                    val items = r.data.map {
                        UiItem.PostItem(
                            id = it.id,
                            line1 = "#${it.id} • ${it.title}",
                            line2 = it.body
                        )
                    }
                    lastPostsCache = items.toMutableList()
                    _uiState.update { it.copy(isLoading = false, status = "Loaded ${items.size} posts", items = items) }
                }
                is ApiResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, status = "Error", items = emptyList()) }
                    _uiEvent.tryEmit(MainUiEvent.Toast(r.message))
                }
            }
        }
    }

    fun getComments(postId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, status = "Loading comments for postId=$postId...", items = emptyList()) }

            when (val r = repo.getComments(postId)) {
                is ApiResult.Success -> {
                    val items = r.data.map {
                        UiItem.CommentItem(
                            id = it.id,
                            line1 = "#${it.id} • ${it.email}",
                            line2 = it.body
                        )
                    }
                    _uiState.update { it.copy(isLoading = false, status = "Loaded ${items.size} comments", items = items) }
                }
                is ApiResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, status = "Error", items = emptyList()) }
                    _uiEvent.tryEmit(MainUiEvent.Toast(r.message))
                }
            }
        }
    }

    fun createPost(userId: Int, title: String, body: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, status = "POST /posts ...") }

            when (val r = repo.createPost(userId, title, body)) {
                is ApiResult.Success -> {
                    // JSONPlaceholder trả id=101 (fake) → ta “thêm vào list cache” để thấy UI đổi
                    val newItem = UiItem.PostItem(
                        id = r.data.id,
                        line1 = "#${r.data.id} • ${r.data.title}",
                        line2 = r.data.body
                    )
                    lastPostsCache.add(0, newItem)
                    _uiState.update { it.copy(isLoading = false, status = "Created (fake) id=${r.data.id}", items = lastPostsCache) }
                    _uiEvent.tryEmit(MainUiEvent.Toast("POST ok: id=${r.data.id} (fake)"))
                }
                is ApiResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, status = "Error") }
                    _uiEvent.tryEmit(MainUiEvent.Toast(r.message))
                }
            }
        }
    }

    fun updatePost(id: Int, userId: Int, title: String, body: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, status = "PUT /posts/$id ...") }

            when (val r = repo.updatePost(id, userId, title, body)) {
                is ApiResult.Success -> {
                    val updated = UiItem.PostItem(
                        id = r.data.id,
                        line1 = "#${r.data.id} • ${r.data.title}",
                        line2 = r.data.body
                    )
                    // update cache nếu đang hiển thị posts
                    val idx = lastPostsCache.indexOfFirst { it.id == id }
                    if (idx != -1) lastPostsCache[idx] = updated

                    _uiState.update { it.copy(isLoading = false, status = "Updated (fake) id=$id", items = lastPostsCache) }
                    _uiEvent.tryEmit(MainUiEvent.Toast("PUT ok: id=$id (fake)"))
                }
                is ApiResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, status = "Error") }
                    _uiEvent.tryEmit(MainUiEvent.Toast(r.message))
                }
            }
        }
    }

    fun deletePost(id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, status = "DELETE /posts/$id ...") }

            when (val r = repo.deletePost(id)) {
                is ApiResult.Success -> {
                    lastPostsCache.removeAll { it.id == id }
                    _uiState.update { it.copy(isLoading = false, status = "Deleted (fake) id=$id", items = lastPostsCache) }
                    _uiEvent.tryEmit(MainUiEvent.Toast("DELETE ok: id=$id (fake)"))
                }
                is ApiResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, status = "Error") }
                    _uiEvent.tryEmit(MainUiEvent.Toast(r.message))
                }
            }
        }
    }
}