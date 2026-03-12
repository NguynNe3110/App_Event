package com.uzuu.learn12_2_restfulapi_basic_real.data.remote.api

import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.BaseResponseDto
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.book.BookDto
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.book.CreateBookRequest
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.book.UpdateBookRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
interface BookApi {
    @GET("books")
    suspend fun getAllBooks(): BaseResponseDto<List<BookDto>>

    @GET("books/{id}")
    suspend fun getBookById(
        @Path("id") id: Int
    ): BaseResponseDto<BookDto>

    @POST("books")
    suspend fun createBook(
        @Body request: CreateBookRequest
    ): BaseResponseDto<BookDto>

    @PUT("books/{id}")
    suspend fun updateBook(
        @Path("id") id: Int,
        @Body request: UpdateBookRequest
    ) : BaseResponseDto<BookDto>

    @DELETE("books/{id}")
    suspend fun deleteBook(
        @Path("id") id: Int
    )
}