package com.uzuu.learn19_3_practice_navigation.feature.task.detail

import androidx.lifecycle.ViewModel
import com.uzuu.learn19_3_practice_navigation.domain.repository.TaskRepository

class TaskDetailViewModel(
    private val repo: TaskRepository
) : ViewModel() {
    fun observeTask(id: Int) = repo.observeTask(id)
}