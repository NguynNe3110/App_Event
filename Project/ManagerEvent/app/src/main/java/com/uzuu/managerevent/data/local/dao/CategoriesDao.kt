package com.uzuu.managerevent.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.uzuu.managerevent.data.local.entity.CategoriesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriesDao {
    @Query("select * from categories order by id asc")
    fun observeCategories() : Flow<List<CategoriesEntity>>
    // k co dinh, do admin tao
}