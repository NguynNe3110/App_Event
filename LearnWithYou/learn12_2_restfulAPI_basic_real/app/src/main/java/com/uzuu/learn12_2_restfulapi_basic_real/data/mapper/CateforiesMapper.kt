package com.uzuu.learn12_2_restfulapi_basic_real.data.mapper

import com.uzuu.learn12_2_restfulapi_basic_real.data.local.entity.CategoriesEntity
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.category.CategoryDto
import com.uzuu.learn12_2_restfulapi_basic_real.domain.model.book.Categories

fun CategoriesEntity.entityToDomain(): Categories{
    return Categories(
        id = id,
        categoriesName = name
    )
}

fun Categories.domainToEntity() : CategoriesEntity {
    return CategoriesEntity(
        id = id,
        name = categoriesName
    )
}

fun CategoryDto.dtoToEntity() : CategoriesEntity {
    return CategoriesEntity(
        id = id,
        name = name
    )
}