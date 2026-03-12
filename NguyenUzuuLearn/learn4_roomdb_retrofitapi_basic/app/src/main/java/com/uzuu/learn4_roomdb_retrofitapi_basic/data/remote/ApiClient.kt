package com.uzuu.learn4_roomdb_retrofitapi_basic.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiClient {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    val api: UserApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApi::class.java)
    }
}