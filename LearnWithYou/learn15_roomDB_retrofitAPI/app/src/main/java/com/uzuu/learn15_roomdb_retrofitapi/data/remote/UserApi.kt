package com.uzuu.learn15_roomdb_retrofitapi.data.remote

import retrofit2.http.GET

interface UserApi {
    @GET("users")
    suspend fun getUsers(): List<UserDto>
}