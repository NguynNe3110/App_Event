package com.uzuu.learn19_5_bottomnav_basic.data.repository

import com.uzuu.learn19_5_bottomnav_basic.data.local.datasource.ProductLocalDataSource
import com.uzuu.learn19_5_bottomnav_basic.data.mapper.toEntity
import com.uzuu.learn19_5_bottomnav_basic.data.mapper.todomain
import com.uzuu.learn19_5_bottomnav_basic.domain.model.Products
import com.uzuu.learn19_5_bottomnav_basic.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.collections.map

class ProductRepositoryImpl(
    private val local: ProductLocalDataSource
) : ProductRepository {
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

    override fun searchProduct(key: String): Flow<List<Products>> {
        return local.searchProduct(key).map { list->
            list.map { it.todomain() }
        }
    }
}