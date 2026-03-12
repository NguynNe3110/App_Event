package com.uzuu.learn12_2_restfulapi_basic_real.domain.repository

import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.book.CreateBookRequest
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.book.UpdateBookRequest
import com.uzuu.learn12_2_restfulapi_basic_real.domain.model.book.Books
import com.uzuu.learn12_2_restfulapi_basic_real.domain.model.book.CreateBook
import com.uzuu.learn12_2_restfulapi_basic_real.domain.model.book.UpdateBook
import com.uzuu.learn12_2_restfulapi_basic_real.domain.model.user.Users
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    val books: Flow<List<Books>>

    suspend fun createBook(request: CreateBookRequest) : Long

    suspend fun updateBook(id: Int, request: UpdateBookRequest) : Int

    suspend fun deleteAllBook() : Int

    suspend fun deleteBookById(id: Int) : Int

    suspend fun getBookById(id: Int) : Books?

    suspend fun checkBookExists(id: Int) : Boolean

    suspend fun syncDataFromServer() : List<Long>
}