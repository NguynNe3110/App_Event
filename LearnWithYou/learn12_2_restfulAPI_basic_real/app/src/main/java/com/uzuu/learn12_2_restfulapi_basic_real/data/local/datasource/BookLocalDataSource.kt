package com.uzuu.learn12_2_restfulapi_basic_real.data.local.datasource

import com.uzuu.learn12_2_restfulapi_basic_real.data.local.dao.BooksDao
import com.uzuu.learn12_2_restfulapi_basic_real.data.local.entity.BooksEntity
import com.uzuu.learn12_2_restfulapi_basic_real.data.mapper.createBookToEntity
import com.uzuu.learn12_2_restfulapi_basic_real.data.mapper.entityToDomain
import com.uzuu.learn12_2_restfulapi_basic_real.data.mapper.mergerToEntity
import com.uzuu.learn12_2_restfulapi_basic_real.domain.model.book.Books
import com.uzuu.learn12_2_restfulapi_basic_real.domain.model.book.CreateBook
import com.uzuu.learn12_2_restfulapi_basic_real.domain.model.book.UpdateBook
import kotlinx.coroutines.flow.Flow

class BookLocalDataSource(
    private val booksDao: BooksDao
) {
    fun observeBooks() = booksDao.observeBooks()

    suspend fun checkBookExists(id: Int): Boolean {
        return booksDao.checkBookExists(id)
    }

    suspend fun createBook(createBook: CreateBook): Long {
        return booksDao.createBook(createBook.createBookToEntity())
    }

    suspend fun deleteAllBook(): Int {
        return booksDao.deleteAll()
    }

    suspend fun deleteBookById(id: Int): Int {
        return booksDao.deleteBookById(id)
    }

    suspend fun getBookByIdt(id: Int): Books? {
        return booksDao.getBookById(id)?.entityToDomain()
    }

    suspend fun updateBook(updateBook: UpdateBook): Int {
        val oldBook = booksDao.getBookById(updateBook.id)
            ?: throw IllegalArgumentException("k tim thay sach")

        val newBook = updateBook.mergerToEntity(oldBook)

        return booksDao.updateBook(newBook)
    }

    suspend fun insertAll(users: List<BooksEntity>) : List<Long> {
        return booksDao.InsertAll(users)
    }
}