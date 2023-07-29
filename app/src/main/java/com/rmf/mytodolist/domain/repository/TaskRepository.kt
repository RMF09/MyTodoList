package com.rmf.mytodolist.domain.repository

import com.rmf.mytodolist.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    suspend fun saveTask(task: Task)

    suspend fun updateTask(task: Task)

    suspend fun deleteTask(task: Task)

    fun getTasks(): Flow<List<Task>>
}