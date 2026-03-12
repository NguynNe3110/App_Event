package com.uzuu.learn12_2_restfulapi_basic_real.data.remote.datasource

import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.api.BookApi
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.BaseResponseDto
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.book.BookDto
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.book.CreateBookRequest
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.book.UpdateBookRequest

class BookRemoteDataSource(
    private val bookApi: BookApi
) {
    suspend fun createBook(request: CreateBookRequest): BaseResponseDto<BookDto> {
        return bookApi.createBook(request)
    }

    suspend fun deleteBookById(id: Int) {
        return bookApi.deleteBook(id)
    }

    suspend fun getAllBooks(): BaseResponseDto<List<BookDto>> {
        return bookApi.getAllBooks()
    }

    suspend fun updateBook(id: Int, request: UpdateBookRequest): BaseResponseDto<BookDto> {
        return bookApi.updateBook(id, request)
    }

    suspend fun getBookById(id: Int): BaseResponseDto<BookDto> {
        return bookApi.getBookById(id)
    }
}