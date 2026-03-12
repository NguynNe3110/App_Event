package com.uzuu.learn12_2_restfulapi_basic_real.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.uzuu.learn12_2_restfulapi_basic_real.data.local.entity.CategoriesEntity
import com.uzuu.learn12_2_restfulapi_basic_real.domain.model.book.Categories
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSamples(categories: List<CategoriesEntity>)

    @Query("select * from categories order by id asc")
    suspend fun getAllCategories(): List<CategoriesEntity>
}