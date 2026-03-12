package com.uzuu.learn12_2_restfulapi_basic_real.data.mapper

import android.R
import androidx.room.Update
import com.uzuu.learn12_2_restfulapi_basic_real.data.local.entity.BooksEntity
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.book.BookDto
import com.uzuu.learn12_2_restfulapi_basic_real.domain.model.book.Books
import com.uzuu.learn12_2_restfulapi_basic_real.domain.model.book.CreateBook
import com.uzuu.learn12_2_restfulapi_basic_real.domain.model.book.UpdateBook

fun BooksEntity.entityToDomain(): Books{
    return Books(
        id= id,
        bookName = name,
        quantity = quantity,
    )
}

fun CreateBook.createBookToEntity() : BooksEntity{
    return BooksEntity(
        id = id,
        name = bookName,
        quantity = quantity,
        categoryId = categoriesId
    )
}

fun UpdateBook.mergerToEntity(old: BooksEntity) : BooksEntity {
    return old.copy(
        id = id,
        name = bookName ?: old.name,
        quantity = quantity ?: old.quantity,
        categoryId = categoriesId ?: old.categoryId
    )
}

fun BookDto.bookDtoToCreateBook() : CreateBook {
    return CreateBook(
        id = id ?: 0,
        bookName = title ?: "",
        quantity = quantity ?: 1,
        categoriesId = category?.id ?: 1
    )
}

fun BookDto.bookDtoToUpdateBook() : UpdateBook {
    return UpdateBook(
        id = id ?: 0,
        bookName = title ?: "",
        quantity = quantity ?: 1,
        categoriesId = category?.id ?: 1
    )
}

fun BookDto.bookDtoToEntity() : BooksEntity {
    return BooksEntity(
        id = id ?: 0,
        name = title ?: "",
        quantity = quantity ?: 1,
        categoryId = category?.id ?: 1
    )
}