package com.rmf.mytodolist.data.parser

import com.rmf.mytodolist.data.local.TaskEntity
import com.rmf.mytodolist.domain.model.Task
import com.rmf.mytodolist.util.toLocalDate

fun TaskEntity.toTask() =
    Task(
        id = id,
        title = title,
        description = description,
        dueDate = dueDate.toLocalDate(),
        isCompleted = isCompleted
    )

fun Task.toEntity() =
    TaskEntity(
        id = id,
        title = title,
        description = description,
        dueDate = displayDueDate,
        isCompleted = isCompleted
    )