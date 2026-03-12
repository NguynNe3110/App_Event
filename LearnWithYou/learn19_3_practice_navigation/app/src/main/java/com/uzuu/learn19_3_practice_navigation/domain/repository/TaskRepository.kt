package com.uzuu.learn19_3_practice_navigation.domain.repository

import com.uzuu.learn19_3_practice_navigation.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun observeTasks(): Flow<List<Task>>
    fun observeTask(id: Int): Flow<Task?>
    suspend fun updateTask(task: Task)
}