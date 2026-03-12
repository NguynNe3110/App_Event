package com.uzuu.learn15_1_roomdb_retrofitapi_basic.data.remote

import retrofit2.http.GET

interface UserApi {
    @GET("users")
    suspend fun getUsers(): List<UserDto>
}