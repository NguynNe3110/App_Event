package com.uzuu.learn19_4_practice_nav_isolation.data.mapper

import com.uzuu.learn19_4_practice_nav_isolation.data.local.entity.ProductEntity
import com.uzuu.learn19_4_practice_nav_isolation.domain.model.Products

fun ProductEntity.todomain() : Products{
    return Products(
        id = id,
        name = name,
        price = price,
        description = description
    )
}

fun Products.toEntity() : ProductEntity{
    return ProductEntity(
        id = id,
        name = name,
        price = price,
        description = description
    )
}