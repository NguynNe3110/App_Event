package com.uzuu.learn12_2_restfulapi_basic_real.data.remote.api

import androidx.room.Delete
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.BaseResponseDto
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.user.CreateUserRequest
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.user.UpdateUserRequest
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.user.UserDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApi {
    //get /users
    @GET("users")
    suspend fun getAllUsers(): BaseResponseDto<List<UserDto>>

    @POST("users")
    suspend fun createUser(
        //vì tạo mới user k cần những trường dữ liệu dư thừa
        // việc tạo ra 1 dto riêng là cần thiết
        @Body request: CreateUserRequest
    ): BaseResponseDto<UserDto>

    @PUT("users/{username}")
    suspend fun updateFullUser(
        @Path("username") username: String,
        @Body request: UpdateUserRequest
    ) : BaseResponseDto<UserDto>

    // patch là sửa 1 phần , lúc này chỉ gửi field dữ liệu cần update là đủ
    @PATCH("users/id")
    suspend fun updateUser(
        @Path("id") id: Int,
        @Body request: UpdateUserRequest
    ) : BaseResponseDto<UserDto>

    @DELETE("users/{username}")
    suspend fun deleteUser(
        @Path("username") username: String
    )
}

