package com.uzuu.learn19_4_practice_nav_isolation.data.local.datasource

import com.uzuu.learn19_4_practice_nav_isolation.data.local.dao.ProductDao
import com.uzuu.learn19_4_practice_nav_isolation.data.local.entity.ProductEntity

class ProductLocalDataSource(
    private val proDao: ProductDao
) {
    fun observeProducts() = proDao.observeProducts()

    suspend fun insertProduct(pro: ProductEntity): Long{
        return proDao.insertProduct(pro)
    }

    suspend fun updateProduct(pro: ProductEntity): Int{
        return proDao.updateProduct(pro)
    }

    suspend fun deleteProductById(id: Int): Int {
        return proDao.deleteProductById(id)
    }

    suspend fun getProductById(id: Int) : ProductEntity {
        return proDao.getProductById(id)
    }
}