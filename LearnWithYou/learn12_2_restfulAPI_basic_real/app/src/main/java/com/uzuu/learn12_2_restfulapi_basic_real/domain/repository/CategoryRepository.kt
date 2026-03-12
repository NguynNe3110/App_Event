package com.uzuu.learn12_2_restfulapi_basic_real.domain.repository

import com.uzuu.learn12_2_restfulapi_basic_real.domain.model.book.Categories

interface CategoryRepository {

    suspend fun insertAllSamples()
    suspend fun getAllCategories() : List<Categories>
}