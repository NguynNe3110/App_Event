package com.uzuu.learn19_3_practice_navigation.feature.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uzuu.learn19_3_practice_navigation.domain.repository.TaskRepository
import com.uzuu.learn19_3_practice_navigation.feature.task.detail.TaskDetailViewModel
import com.uzuu.learn19_3_practice_navigation.feature.task.edit.TaskEditViewModel
import com.uzuu.learn19_3_practice_navigation.feature.task.list.TaskListViewModel


class TaskListVMFactory(private val repo: TaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskListViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown VM")
    }
}

class TaskDetailVMFactory(private val repo: TaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskDetailViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown VM")
    }
}

class TaskEditVMFactory(private val repo: TaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskEditViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskEditViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown VM")
    }
}