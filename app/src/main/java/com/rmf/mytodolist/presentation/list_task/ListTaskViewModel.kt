package com.rmf.mytodolist.presentation.list_task

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmf.mytodolist.domain.model.Task
import com.rmf.mytodolist.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListTaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _tasks: MutableStateFlow<List<Task>> = MutableStateFlow(emptyList())
    val task: StateFlow<List<Task>> = _tasks

    val statusDeletingTask = MutableSharedFlow<Boolean>()

    init {
        getTask()
    }

    private fun getTask() {
        viewModelScope.launch {
            taskRepository.getTasks().collect { result ->
                Log.e("TAG", "getTask: $result")
                _tasks.value = result
            }
        }
    }

    fun removeTask(task: Task) {
        viewModelScope.launch {
            taskRepository.deleteTask(task)
            statusDeletingTask.emit(true)
        }
    }

    fun checkedTask(task: Task) {
        viewModelScope.launch {
            taskRepository.updateTask(task.copy(isCompleted = !task.isCompleted))
        }
    }
}