package com.uzuu.learn19_5_bottomnav_basic.data.mapper

import com.uzuu.learn19_5_bottomnav_basic.data.local.entity.ProductEntity
import com.uzuu.learn19_5_bottomnav_basic.domain.model.Products
import com.uzuu.learn19_5_bottomnav_basic.feature.main.UiItem

fun ProductEntity.todomain() : Products {
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

fun Products.toUi(): UiItem {
    return UiItem(
        id = id,
        name = name,
        price = price
    )
}