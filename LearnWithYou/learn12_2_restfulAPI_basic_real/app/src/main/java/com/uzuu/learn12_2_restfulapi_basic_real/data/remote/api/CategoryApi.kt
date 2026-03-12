package com.uzuu.learn12_2_restfulapi_basic_real.data.remote.api

import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.BaseResponseDto
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.category.CategoryDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoryApi {
    @GET("categories")
    suspend fun getAllCategory(): BaseResponseDto<List<CategoryDto>>
}