package com.uzuu.learn19_5_bottomnav_basic.data.local.datasource

import com.uzuu.learn19_5_bottomnav_basic.data.local.dao.ProductDao
import com.uzuu.learn19_5_bottomnav_basic.data.local.entity.ProductEntity
import com.uzuu.learn19_5_bottomnav_basic.domain.model.Products
import kotlinx.coroutines.flow.Flow

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

    fun searchProduct(key: String) : Flow<List<ProductEntity>> {
        return proDao.searchProduct(key)
    }
}