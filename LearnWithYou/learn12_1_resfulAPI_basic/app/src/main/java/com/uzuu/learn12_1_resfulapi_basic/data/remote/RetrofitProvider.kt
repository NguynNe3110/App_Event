package com.uzuu.learn12_1_resfulapi_basic.data.remote

import com.uzuu.learn12_1_resfulapi_basic.data.remote.api.JsonPlaceholderApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {

    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    // part okhttp advance
    private val client: OkHttpClient by lazy {
        val logger = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
    }

    // quan trọng
    // cách 1 tạo retrofit và api cùng lúc
    // tức là bieestn api k phải retrofit mà là instance của jsonplaceholder
//    val api: JsonPlaceholderApi by lazy {
//        Retrofit.Builder()
//            //base url
//            .baseUrl(BASE_URL)
//            // init
//            .client(client)
//            .addConverterFactory(GsonConverterFactory.create())
//            // 2 dòng này là tạo retrofit -> tạo luôn implementation của interface api
//            // trả về api
//            .build()
//            .create(JsonPlaceholderApi::class.java)
//    }

    // retrofit la http manager
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // jsonplaceholder la api implementation
    val jsonPlaceHolder: JsonPlaceholderApi by lazy {
        retrofit.create(JsonPlaceholderApi::class.java)
    }

    //test
//    val postApi: PostApi by lazy {
//        retrofit.create(PostApi::class.java)
//    }

}