package com.uzuu.learn12_2_restfulapi_basic_real.data.remote

import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.api.BookApi
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.api.CategoryApi
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.api.UserApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {
    //build app tren dt phai sua lai base_url: 192.168.xxx.xxx
    private const val BASE_URL = "http://192.168.1.9:8081/my-lib/api/"

    //okhttp
    private val client: OkHttpClient by lazy {
        val logger = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
    }


    //http manager
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    val userApi: UserApi by lazy {
        retrofit.create(UserApi::class.java)
    }

    val bookApi: BookApi by lazy {
        retrofit.create(BookApi::class.java)
    }

    val categoryApi: CategoryApi by lazy {
        retrofit.create(CategoryApi::class.java)
    }
}