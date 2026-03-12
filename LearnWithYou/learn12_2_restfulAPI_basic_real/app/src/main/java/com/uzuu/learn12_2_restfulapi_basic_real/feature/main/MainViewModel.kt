package com.uzuu.learn12_2_restfulapi_basic_real.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.book.CreateBookRequest
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.book.UpdateBookRequest
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.book.categoryRef
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.user.CreateUserRequest
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.user.UpdateUserRequest
import com.uzuu.learn12_2_restfulapi_basic_real.domain.repository.BookRepository
import com.uzuu.learn12_2_restfulapi_basic_real.domain.repository.CategoryRepository
import com.uzuu.learn12_2_restfulapi_basic_real.domain.repository.UserRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

class MainViewModel(
    private val userRepo: UserRepository,
    private val bookRepo: BookRepository,
    private val cateRepo: CategoryRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<MainUiEvent>(extraBufferCapacity = 3)
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        observeAll()
    }

    private fun observeAll() {
        viewModelScope.launch {

            combine(
                userRepo.users,
                bookRepo.books
            ) { users, books ->

                println("DEBUG users=${users.size}")
                println("DEBUG books=${books.size}")

                val userItems =
                    users.map {
                        UiItem.UserItem(it.id, it.displayName, it.fullName)
                    }

                val bookItems =
                    books.map {
                        UiItem.BookItem(it.id, it.bookName, it.quantity)
                    }

                userItems + bookItems

            }.collect { items ->

                _uiState.update {
                    it.copy(items = items)
                }

            }
        }

        viewModelScope.launch {
            val cates = cateRepo.getAllCategories()
            println("DEBUG du lieu object trong viewmodel -- ${cates}")
            println("DEBUG du lieu category trong viewmodel size: ${cates.size} ")
            _uiState.update { it.copy(categories = cates) }
        }
    }


    fun createBook(name: String, quantity: String, idCat: String){
        viewModelScope.launch {
            val quantity: Int = quantity.toIntOrNull() ?: 0
            val idCate: Int = idCat.toIntOrNull() ?: 0

            val request = CreateBookRequest(
                title = name,
                quantity = quantity,
                category = categoryRef(idCate)
            )
            bookRepo.createBook(request)
        }
     }

    fun updateBook(id: String, name: String, quantity: String, idCat: String){
        viewModelScope.launch {
            val id: Int = id.toIntOrNull() ?: 0
            val quantity: Int = quantity.toIntOrNull() ?: 0
            val idCate: Int = idCat.toIntOrNull() ?: 0

            val request = UpdateBookRequest(
                title = name,
                quantity = quantity,
                categoryId = idCate
            )
            loadIsTrue()
            bookRepo.updateBook(id, request)
            loadIsFalse()
        }
    }

    fun deleteBook(id: String){
        viewModelScope.launch {
            val id: Int = id.toIntOrNull() ?: 0
            loadIsTrue()
            bookRepo.deleteBookById(id)
            loadIsFalse()
        }
    }

    fun deleteAllBook() {
        viewModelScope.launch {
            loadIsTrue()
            bookRepo.deleteAllBook()
            loadIsFalse()
        }
    }

    fun syncBook() {
        viewModelScope.launch {
            loadIsTrue()
            bookRepo.syncDataFromServer()
            loadIsFalse()
        }
        _uiEvent.tryEmit(MainUiEvent.Toast("dong bo thanh cong!"))
        return
    }

    fun createUser(id: String, username: String, password: String, fullName: String){
        viewModelScope.launch {
            val id: Int = id.toIntOrNull() ?: 0

            val request = CreateUserRequest(
                username = username,
                password = password,
                fullName = fullName
            )
            loadIsTrue()
            userRepo.createUser(request)
            loadIsFalse()
        }
    }

    fun updateUser(id: String, username: String, password: String, fullName: String){
        viewModelScope.launch {
            val id: Int = id.toIntOrNull() ?: 0
            if (username.isEmpty() || password.isEmpty() || fullName.isEmpty()) {
                val request = UpdateUserRequest(
                    username = username,
                    password = password,
                    fullName = fullName
                )
                loadIsTrue()
                userRepo.updatePatchUser(id, request)
                loadIsFalse()
            } else {
                val request = UpdateUserRequest(
                    username = username,
                    password = password,
                    fullName = fullName
                )
                loadIsTrue()
                userRepo.updatePutUser(username, request)
                loadIsFalse()
            }
        }
    }

    //xoa theo id hoac theo ten
    fun deleteUser(id: String, username: String){
        viewModelScope.launch {
            if(id.isEmpty()){
                userRepo.deleteUserByUsername(username)
            } else {
                val id : Int = id.toIntOrNull() ?: 0
                userRepo.deleteUserById(id)
            }
        }
    }

    fun deleteAllUser() {
        viewModelScope.launch {
            userRepo.deleteAllUser()
        }
    }

    fun syncUser() {
        viewModelScope.launch {
            loadIsTrue()
            userRepo.syncDataFromServer()
            loadIsFalse()
        }
        _uiEvent.tryEmit(MainUiEvent.Toast("dong bo thanh cong!"))
        return
    }

    fun loadIsTrue() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            delay(300)
        }
    }

    fun loadIsFalse() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun InsertSampleCategory() {
        viewModelScope.launch {
            cateRepo.insertAllSamples()
        }
    }
}