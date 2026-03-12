package com.uzuu.learn19_5_bottomnav_basic.data.repository

import com.uzuu.learn19_5_bottomnav_basic.data.local.dao.TimeDao
import com.uzuu.learn19_5_bottomnav_basic.data.mapper.toDomain
import com.uzuu.learn19_5_bottomnav_basic.data.mapper.toEntity
import com.uzuu.learn19_5_bottomnav_basic.domain.model.Times
import com.uzuu.learn19_5_bottomnav_basic.domain.repository.TimeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TimeRepositoryImpl(
    private val timeDao: TimeDao
): TimeRepository {
    override val times: Flow<List<Times>> =
        timeDao.observeTime().map { entities ->
            entities.map { it.toDomain()  }
        }

    override suspend fun insertTime(time: Times): Long {
        println("DEBUG in repoImpl (insert) nhan vao $time")
         return timeDao.insertTime(time.toEntity())
    }

    override suspend fun updateTime(time: Times): Int {
        return timeDao.updateTime(time.toEntity())
    }

    override suspend fun deleteTime(id: Int) {
        timeDao.deleteTimeById(id)
    }

    override suspend fun getTimeById(id: Int): Times? {
        val vll = timeDao.getTimeById(id)
        val vl = vll?.toDomain()
        println("DEBUG in repoImpl(get) domain: $vl, entity $vll")
        return vl
    }
}