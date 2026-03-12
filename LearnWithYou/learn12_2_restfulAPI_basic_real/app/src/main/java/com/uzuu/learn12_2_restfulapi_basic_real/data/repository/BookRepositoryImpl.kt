package com.uzuu.learn12_2_restfulapi_basic_real.data.repository

import com.uzuu.learn12_2_restfulapi_basic_real.data.local.datasource.BookLocalDataSource
import com.uzuu.learn12_2_restfulapi_basic_real.data.mapper.bookDtoToCreateBook
import com.uzuu.learn12_2_restfulapi_basic_real.data.mapper.bookDtoToEntity
import com.uzuu.learn12_2_restfulapi_basic_real.data.mapper.bookDtoToUpdateBook
import com.uzuu.learn12_2_restfulapi_basic_real.data.mapper.entityToDomain
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.datasource.BookRemoteDataSource
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.book.CreateBookRequest
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.book.UpdateBookRequest
import com.uzuu.learn12_2_restfulapi_basic_real.domain.model.book.Books
import com.uzuu.learn12_2_restfulapi_basic_real.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookRepositoryImpl(
    private val local: BookLocalDataSource,
    private val remote: BookRemoteDataSource
) : BookRepository{

    override val books: Flow<List<Books>> =
        local.observeBooks().map { listEntity ->
            listEntity.map {it.entityToDomain() }
        }

    override suspend fun createBook(request: CreateBookRequest): Long {
        println("NGUY dl nhan tu viewmodel  ${request}")
        val bookDto = remote.createBook(request).result
        // sau khi thanh cong
        return local.createBook(bookDto.bookDtoToCreateBook())
    }

    override suspend fun updateBook(id: Int, request: UpdateBookRequest): Int {
        val bookDto = remote.updateBook(id, request).result
        return local.updateBook(bookDto.bookDtoToUpdateBook())
    }

    override suspend fun checkBookExists(id: Int): Boolean {
        return local.checkBookExists(id)
    }

    // chi xoa trong room
    override suspend fun deleteAllBook(): Int {
        return local.deleteAllBook()
    }

    override suspend fun deleteBookById(id: Int): Int {
        remote.deleteBookById(id)
        //xu ly status
        return local.deleteBookById(id)
    }

    override suspend fun getBookById(id: Int): Books? {
        //lay 1 quyen thi k can api
//        val bookDto = remote.getBookById(id).result
        return local.getBookByIdt(id)
    }

    override suspend fun syncDataFromServer(): List<Long> {
        val books = remote.getAllBooks()
            .result
            .map { it.bookDtoToEntity() }
        local.deleteAllBook()
        return local.insertAll(books)
    }
}