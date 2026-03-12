package com.uzuu.learn20_2_recycler_dao.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.uzuu.learn20_2_recycler_dao.data.local.entity.UserEntity
import com.uzuu.learn20_2_recycler_dao.domain.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("select * from users order by id asc")
    fun obverseAll() : Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserEntity) : Long

    @Update
    suspend fun update(user: UserEntity) : Int

    @Query("delete from users")
    suspend fun deleteAll(): Int

    @Query("delete from users where id = :id")
    suspend fun deleteById(id: Int) : Int

    @Query("select * from users where id = :id limit 1")
    suspend fun getUserById(id: Int): UserEntity?

    @Query("select exists(select 1 from users where id = :id)")
    suspend fun checkExists(id: Int) : Boolean
}