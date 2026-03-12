package com.uzuu.learn19_3_practice_navigation.feature.task.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uzuu.learn19_3_practice_navigation.domain.model.Task
import com.uzuu.learn19_3_practice_navigation.domain.repository.TaskRepository
import kotlinx.coroutines.launch

class TaskEditViewModel(
    private val repo: TaskRepository
) : ViewModel() {

    fun observeTask(id: Int) = repo.observeTask(id)

    fun save(task: Task) {
        viewModelScope.launch {
            repo.updateTask(task)
        }
    }
}