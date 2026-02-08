package com.uzuu.learn14_1_roomdb_foreignkey.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.uzuu.learn14_1_roomdb_foreignkey.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    //create
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(user: UserEntity) : Long

    //update
    @Update
    suspend fun update(user: UserEntity) : Int

    //get all
    @Query("select * from users order by id desc")
    suspend fun observeAll() : Flow<List<UserEntity>>

    //get by id
    @Query("select * from users where id = :id")
    suspend fun getUserById(id :Long) : Int

    //delete all
    @Query("delete from users")
    suspend fun deleteAll() : Int

    //delete by id
    @Query("delete from users where id = :id")
    suspend fun deleteUserById(id: Long) : Int
}