package com.uzuu.learn19_3_practice_navigation.data.repository

import com.uzuu.learn19_3_practice_navigation.data.local.dao.TaskDao
import com.uzuu.learn19_3_practice_navigation.data.mapper.toDomain
import com.uzuu.learn19_3_practice_navigation.data.mapper.toEntity
import com.uzuu.learn19_3_practice_navigation.domain.model.Task
import com.uzuu.learn19_3_practice_navigation.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl(
    private val dao: TaskDao
) : TaskRepository {

    override fun observeTasks(): Flow<List<Task>> =
        dao.observeAll().map { list -> list.map { it.toDomain() } }

    override fun observeTask(id: Int): Flow<Task?> =
        dao.observeById(id).map { it?.toDomain() }

    override suspend fun updateTask(task: Task) {
        dao.update(task.toEntity())
    }
}