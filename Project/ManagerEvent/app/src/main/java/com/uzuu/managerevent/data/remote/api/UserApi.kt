package com.uzuu.managerevent.data.remote.api

import com.uzuu.managerevent.data.remote.dto.BaseResponseDto
import com.uzuu.managerevent.data.remote.dto.request.UserRequest
import com.uzuu.managerevent.data.remote.dto.response.UserResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApi {

    @GET("/users/{username}")
    suspend fun getInfoByUsername(
        @Path("username") username: String
    ): BaseResponseDto<UserResponseDto>

    @PUT("/users/{username}")
    suspend fun updateInfo(
        @Path("username") username: String,
        @Body request: UserRequest
    ):  BaseResponseDto<UserResponseDto>

    // có api get thong tin tai khoan hien tai
}