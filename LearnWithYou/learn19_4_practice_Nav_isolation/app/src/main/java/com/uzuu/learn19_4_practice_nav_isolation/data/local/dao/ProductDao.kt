package com.uzuu.learn19_4_practice_nav_isolation.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.uzuu.learn19_4_practice_nav_isolation.data.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("select * from products order by id asc")
    fun observeProducts(): Flow<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(pro: ProductEntity): Long

    @Query("delete from products where id = :id")
    suspend fun deleteProductById(id: Int) : Int

    @Update
    suspend fun updateProduct(pro: ProductEntity): Int

    @Query("select * from products where id = :id")
    suspend fun getProductById(id: Int) : ProductEntity
}