package com.uzuu.customer.data.remote

import com.uzuu.customer.data.remote.api.AuthApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {
    private const val BASE_URL = "http://192.168.1.9:8081/my-lib/api/"

    //okhttp
    private val client: OkHttpClient by lazy {
        val logger = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor()) // thêm dòng này
            .addInterceptor(logger)
            .build()
    }


    //http manager
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(com.uzuu.customer.data.remote.RetrofitProvider.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authApi: AuthApi by lazy {
        retrofit.create(AuthApi::class.java)
    }
}