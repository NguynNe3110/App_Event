package com.uzuu.learn19_3_practice_navigation.data.mapper

import com.uzuu.learn19_3_practice_navigation.data.local.entity.TaskEntity
import com.uzuu.learn19_3_practice_navigation.domain.model.Task

fun TaskEntity.toDomain(): Task = Task(
    id = id,
    title = title,
    done = done
)

fun Task.toEntity(): TaskEntity = TaskEntity(
    id = id,
    title = title,
    done = done
)