package com.uzuu.petrolimex.data.local.datasource

import com.uzuu.petrolimex.data.local.dao.FuelDao
import com.uzuu.petrolimex.data.local.dao.TimeDao
import com.uzuu.petrolimex.data.local.entity.FuelEntity
import com.uzuu.petrolimex.data.local.entity.TimeEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataLocalSource @Inject constructor(
    private val timeDao: TimeDao,
    private val fuelDao: FuelDao
) {
    //========time========
    fun observeTime(): Flow<TimeEntity?>{
        return timeDao.observeTime()
    }

    suspend fun updateTime(timeEntity: TimeEntity){
        timeDao.updateTime(timeEntity)
    }

    suspend fun insertTime(timeEntity: TimeEntity): Long{
        return timeDao.insertTime(timeEntity)
    }

    //=====fuel

    fun observeFuels(): Flow<List<FuelEntity?>>{
        return fuelDao.observeFuels()
    }

    suspend fun updateFuel(fuelEntity: FuelEntity){
        fuelDao.updateFuel(fuelEntity)
    }

    suspend fun insertFuel(fuels: List<FuelEntity>): List<Long>{
        return fuelDao.insertFuels(fuels)
    }
}