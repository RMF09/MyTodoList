package com.rmf.mytodolist.presentation.list_task

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmf.mytodolist.R
import com.rmf.mytodolist.domain.model.Task
import com.rmf.mytodolist.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListTaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val application: Application
) : ViewModel() {

    private val _tasks: MutableStateFlow<List<Task>> = MutableStateFlow(emptyList())
    val task: StateFlow<List<Task>> = _tasks

    private val _error: MutableStateFlow<String?> = MutableStateFlow(null)
    val error: StateFlow<String?> = _error


    init {
        getTask()
    }

    private fun getTask() {
        viewModelScope.launch {
            try {
                taskRepository.getTasks().collect { result ->
                    Log.e("TAG", "getTask: $result")
                    _tasks.value = result
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = application.getString(R.string.text_message_error_db_while_load_data)
            }
        }
    }

    fun removeTask(task: Task) {
        viewModelScope.launch {
            try {
                taskRepository.deleteTask(task)
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value =
                    application.getString(R.string.text_message_error_db_while_delete_data)
            }
        }
    }

    fun checkedTask(task: Task) {
        viewModelScope.launch {
            try {
                taskRepository.updateTask(task.copy(isCompleted = !task.isCompleted))
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value =
                    application.getString(R.string.text_message_error_db_while_update_data)
            }
        }
    }

    fun dismissDialog() {
        viewModelScope.launch {
            _error.value = null
        }
    }
}