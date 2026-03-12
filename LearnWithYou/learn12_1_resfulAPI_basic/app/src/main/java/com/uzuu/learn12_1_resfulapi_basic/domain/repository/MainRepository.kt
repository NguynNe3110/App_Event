package com.uzuu.learn12_1_resfulapi_basic.domain.repository

import com.uzuu.learn12_1_resfulapi_basic.core.result.ApiResult
import com.uzuu.learn12_1_resfulapi_basic.data.mapper.toDomain
import com.uzuu.learn12_1_resfulapi_basic.data.remote.api.JsonPlaceholderApi
import com.uzuu.learn12_1_resfulapi_basic.data.remote.dto.PostDto
import com.uzuu.learn12_1_resfulapi_basic.domain.model.Comment
import com.uzuu.learn12_1_resfulapi_basic.domain.model.Post
import retrofit2.HttpException
import java.io.IOException

class MainRepository(
    private val api: JsonPlaceholderApi
) {
    suspend fun getPosts(): ApiResult<List<Post>> = safeCall {
        // thật chất nó sẽ được hiểu block = {...}
        // tức là đang tạo 1 lambda và truyền lambda vào tham số block
        api.getPosts().map { it.toDomain() }
    }

    suspend fun getComments(postId: Int): ApiResult<List<Comment>> = safeCall {
        api.getComments(postId).map { it.toDomain() }
    }

    suspend fun createPost(userId: Int, title: String, body: String): ApiResult<Post> = safeCall {
        // JSONPlaceholder yêu cầu đủ field; id có thể 0
        api.createPost(
            PostDto(
                userId = userId,
                id = 0,
                title = title,
                body = body
            )
        ).toDomain()
    }

    suspend fun updatePost(id: Int, userId: Int, title: String, body: String): ApiResult<Post> = safeCall {
        api.updatePost(
            id = id,
            body = PostDto(
                userId = userId,
                id = id,
                title = title,
                body = body
            )
        ).toDomain()
    }

    suspend fun deletePost(id: Int): ApiResult<Unit> = safeCall {
        api.deletePost(id)
        Unit
    }


    private suspend fun <T> safeCall(block: suspend () -> T): ApiResult<T> {
        //    block là một biến
//    Kiểu của nó là: một hàm
//    Không nhận tham số
//    Trả về kiểu T
//    Là suspend function
        return try {
            ApiResult.Success(block())
        } catch (e: IOException) {
            ApiResult.Error("Network error (mất mạng/timeout)", e)
        } catch (e: HttpException) {
            ApiResult.Error("HTTP ${e.code()} ${e.message()}", e)
        } catch (e: Exception) {
            ApiResult.Error("Unknown error: ${e.message}", e)
        }
    }
}