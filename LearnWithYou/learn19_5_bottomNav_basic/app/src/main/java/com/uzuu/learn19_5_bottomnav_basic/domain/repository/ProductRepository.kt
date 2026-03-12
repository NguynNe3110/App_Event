package com.uzuu.learn19_5_bottomnav_basic.domain.repository

import com.uzuu.learn19_5_bottomnav_basic.data.local.entity.ProductEntity
import com.uzuu.learn19_5_bottomnav_basic.domain.model.Products
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    val products : Flow<List<Products>>

    // phai tao ra domain cho insert va update
    suspend fun insertProduct(pro: Products): Long

    suspend fun updateProduct(pro: Products): Int

    suspend fun deleteProductById(id: Int) : Int

    suspend fun getProductById(id: Int) : Products
    fun searchProduct(key: String) : Flow<List<Products>>
}