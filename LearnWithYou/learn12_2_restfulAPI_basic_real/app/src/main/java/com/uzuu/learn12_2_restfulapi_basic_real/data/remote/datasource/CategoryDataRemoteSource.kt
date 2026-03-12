package com.uzuu.learn12_2_restfulapi_basic_real.data.remote.datasource

import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.api.CategoryApi
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.BaseResponseDto
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.category.CategoryDto

class CategoryDataRemoteSource(
    private val categoryApi: CategoryApi
) {
    suspend fun getAllCategory() : BaseResponseDto<List<CategoryDto>> {
        return categoryApi.getAllCategory()
    }
}