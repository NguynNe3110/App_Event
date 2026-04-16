package com.uzuu.petrolimex.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.uzuu.petrolimex.data.local.entity.FuelEntity
import com.uzuu.petrolimex.data.local.entity.TimeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FuelDao {
    @Query("SELECT * FROM fuel")
    fun observeFuels(): Flow<List<FuelEntity?>>

    @Update
    suspend fun updateFuel(fuelEntity: FuelEntity): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFuels(fuels: List<FuelEntity>): List<Long>
}