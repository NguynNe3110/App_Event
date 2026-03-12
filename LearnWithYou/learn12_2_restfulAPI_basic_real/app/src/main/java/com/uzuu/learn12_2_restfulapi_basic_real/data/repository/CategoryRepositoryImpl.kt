package com.uzuu.learn12_2_restfulapi_basic_real.data.repository

import com.uzuu.learn12_2_restfulapi_basic_real.data.local.dao.CategoriesDao
import com.uzuu.learn12_2_restfulapi_basic_real.data.local.entity.CategoriesEntity
import com.uzuu.learn12_2_restfulapi_basic_real.data.mapper.domainToEntity
import com.uzuu.learn12_2_restfulapi_basic_real.data.mapper.dtoToEntity
import com.uzuu.learn12_2_restfulapi_basic_real.data.mapper.entityToDomain
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.datasource.CategoryDataRemoteSource
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.book.categoryRef
import com.uzuu.learn12_2_restfulapi_basic_real.domain.model.book.Categories
import com.uzuu.learn12_2_restfulapi_basic_real.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CategoryRepositoryImpl(
    private val categoriesDao: CategoriesDao,
    private val remote: CategoryDataRemoteSource
) : CategoryRepository{

    override suspend fun getAllCategories(): List<Categories> {
        return categoriesDao.getAllCategories().map { it.entityToDomain() }
    }

    override suspend fun insertAllSamples(){
        val cateDto = remote.getAllCategory().result
        categoriesDao.insertAllSamples(cateDto.map { it.dtoToEntity() })
    }
}