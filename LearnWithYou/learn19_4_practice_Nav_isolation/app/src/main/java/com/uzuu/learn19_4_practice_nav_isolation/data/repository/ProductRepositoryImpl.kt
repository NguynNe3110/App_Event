package com.uzuu.learn19_4_practice_nav_isolation.data.repository

import com.uzuu.learn19_4_practice_nav_isolation.data.local.datasource.ProductLocalDataSource
import com.uzuu.learn19_4_practice_nav_isolation.data.mapper.toEntity
import com.uzuu.learn19_4_practice_nav_isolation.data.mapper.todomain
import com.uzuu.learn19_4_practice_nav_isolation.domain.model.Products
import com.uzuu.learn19_4_practice_nav_isolation.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductRepositoryImpl(
    private val local: ProductLocalDataSource
) : ProductRepository{
    override val products: Flow<List<Products>> =
        local.observeProducts().map { listEntity ->
            listEntity.map { it.todomain() }
        }

    override suspend fun deleteProductById(id: Int) :Int{
        return local.deleteProductById(id)
    }

    override suspend fun insertProduct(pro: Products) : Long{
        return local.insertProduct(pro.toEntity())
    }

    override suspend fun updateProduct(pro: Products) : Int{
        return local.updateProduct(pro.toEntity())
    }

    override suspend fun getProductById(id: Int): Products {
        return local.getProductById(id).todomain()
    }
}