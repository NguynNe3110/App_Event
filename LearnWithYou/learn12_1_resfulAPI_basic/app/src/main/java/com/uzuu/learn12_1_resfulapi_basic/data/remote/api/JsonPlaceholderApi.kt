package com.uzuu.learn12_1_resfulapi_basic.data.remote.api

import com.uzuu.learn12_1_resfulapi_basic.data.remote.dto.CommentDto
import com.uzuu.learn12_1_resfulapi_basic.data.remote.dto.PostDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface JsonPlaceholderApi {

    // GET /posts
    @GET("posts")
    suspend fun getPosts(): List<PostDto>

    // GET /comments?postId=1
    @GET("comments")
    suspend fun getComments(
        @Query("postId") postId: Int
    ): List<CommentDto>

    // POST /posts
    @POST("posts")
    suspend fun createPost(
        @Body body: PostDto
    ): PostDto

    // PUT /posts/1
    @PUT("posts/{id}")
    suspend fun updatePost(
        @Path("id") id: Int,
        @Body body: PostDto
    ): PostDto

    // DELETE /posts/1
    @DELETE("posts/{id}")
    suspend fun deletePost(
        @Path("id") id: Int
    )
}