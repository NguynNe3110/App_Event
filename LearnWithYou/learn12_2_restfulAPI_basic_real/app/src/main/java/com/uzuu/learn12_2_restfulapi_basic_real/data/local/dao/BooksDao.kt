package com.uzuu.learn12_2_restfulapi_basic_real.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.uzuu.learn12_2_restfulapi_basic_real.data.local.entity.BooksEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BooksDao {
    @Query("select * from books")
    fun observeBooks() : Flow<List<BooksEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createBook(book: BooksEntity) : Long

    @Update()
    suspend fun updateBook(book: BooksEntity) : Int

    @Query("delete from books where id = :id")
    suspend fun deleteBookById(id: Int): Int

    @Query("DELETE FROM books")
    suspend fun deleteAll(): Int

    @Query("select * from books where id = :id")
    suspend fun getBookById(id: Int) : BooksEntity?

    @Query("select exists(select 1 from books where id = :id)")
    suspend fun checkBookExists(id: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun InsertAll(books: List<BooksEntity>): List<Long>
}