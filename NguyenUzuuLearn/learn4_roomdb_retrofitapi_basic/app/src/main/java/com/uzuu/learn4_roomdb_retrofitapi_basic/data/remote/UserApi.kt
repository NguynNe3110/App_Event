package com.uzuu.learn4_roomdb_retrofitapi_basic.data.remote

import retrofit2.http.GET
import javax.annotation.processing.Generated

interface UserApi {
    @GET("users")
    suspend fun getUsers(): List<UserDto>
}