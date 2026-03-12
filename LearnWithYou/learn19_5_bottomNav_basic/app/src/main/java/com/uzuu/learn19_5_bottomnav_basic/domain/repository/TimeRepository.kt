package com.uzuu.learn19_5_bottomnav_basic.domain.repository

import com.uzuu.learn19_5_bottomnav_basic.domain.model.Times
import kotlinx.coroutines.flow.Flow
import java.sql.Time

interface TimeRepository {
    val times : Flow<List<Times>>

    suspend fun insertTime(time: Times): Long

    suspend fun updateTime(time: Times) : Int

    suspend fun deleteTime(id: Int)

    suspend fun getTimeById(id: Int) : Times?
}