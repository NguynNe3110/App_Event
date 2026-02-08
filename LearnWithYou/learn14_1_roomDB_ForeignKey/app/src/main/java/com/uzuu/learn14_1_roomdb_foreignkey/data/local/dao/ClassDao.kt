package com.uzuu.learn14_1_roomdb_foreignkey.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.uzuu.learn14_1_roomdb_foreignkey.data.local.entity.ClassEntity
import com.uzuu.learn14_1_roomdb_foreignkey.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClassDao {
    //create
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(classes: ClassEntity) : Long

    //update
    @Update
    suspend fun update(classes: ClassEntity) : Int

    //get all
    @Query("select * from classes order by id desc")
    suspend fun observeAll() : Flow<List<ClassEntity>>

    //get by id
    @Query("select * from classes where id = :id")
    suspend fun getClassById(id :Long) : Int

    //delete all
    @Query("delete from classes")
    suspend fun deleteAll() : Int

    //delete by id
    @Query("delete from classes where id = :id")
    suspend fun deleteClassById(id: Long) : Int
}