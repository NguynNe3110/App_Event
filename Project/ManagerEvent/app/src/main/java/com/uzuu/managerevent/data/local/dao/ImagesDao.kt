package com.uzuu.managerevent.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.uzuu.managerevent.data.local.entity.ImagesEntity

@Dao
interface ImagesDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(image: ImagesEntity): Long

    @Update
    suspend fun updateImage(image: ImagesEntity): Int

    @Query("select * from images where id = :id")
    suspend fun getImageById(id: Int) : ImagesEntity?

}