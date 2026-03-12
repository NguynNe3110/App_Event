package com.uzuu.learn19_3_practice_navigation.feature.task.list

import androidx.lifecycle.ViewModel
import com.uzuu.learn19_3_practice_navigation.domain.repository.TaskRepository

class TaskListViewModel(
    repo: TaskRepository
) : ViewModel() {
    val tasks = repo.observeTasks()
}