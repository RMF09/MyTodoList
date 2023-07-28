package com.rmf.mytodolist.data.repository

import com.rmf.mytodolist.data.local.TaskDao
import com.rmf.mytodolist.data.parser.toEntity
import com.rmf.mytodolist.data.parser.toTask
import com.rmf.mytodolist.domain.model.Task
import com.rmf.mytodolist.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val dao: TaskDao
) : TaskRepository {
    override suspend fun saveTask(task: Task) {
        dao.save(task = task.toEntity())
    }

    override suspend fun updateTask(task: Task) {
        dao.update(task = task.toEntity())
    }

    override suspend fun deleteTask(task: Task) {
        dao.delete(task = task.toEntity())
    }

    override fun getTasks(): Flow<List<Task>> {
        return dao.getTask().map { list -> list.map { it.toTask() } }
    }
}