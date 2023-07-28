package com.rmf.mytodolist.presentation.add_edit_task

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmf.mytodolist.R
import com.rmf.mytodolist.domain.model.Task
import com.rmf.mytodolist.domain.repository.TaskRepository
import com.rmf.mytodolist.util.exhaustive
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val taskRepository: TaskRepository,
    private val application: Application
) : ViewModel() {

    private val isEditMode = savedStateHandle.get<Boolean>("isEditMode") ?: false
    private val task = savedStateHandle.get<Task>("task")
    var state by mutableStateOf(AddEditTaskState())

    init {
        Log.e("TAG", "addEditTask: $isEditMode, $task")
        if (isEditMode) {
            task?.let { task ->
                state = state.copy(
                    title = task.title,
                    description = task.description,
                    dueDate = task.dueDate
                )
            }
        }
    }

    fun onEvent(e: AddEditTaskUIEvent) {
        when (e) {
            is AddEditTaskUIEvent.OnChangeDescription -> {
                state = state.copy(description = e.value)
            }
            is AddEditTaskUIEvent.OnChangeDueDate -> {
                state = state.copy(dueDate = e.value)
            }
            is AddEditTaskUIEvent.OnChangeTitle -> {
                state = state.copy(title = e.value)
            }
            AddEditTaskUIEvent.OnClickAddEditButton -> {
                if (isEditMode) edit() else add()
            }
            AddEditTaskUIEvent.OnDismissDialog -> {
                state = state.copy(isSuccess = false)
            }
        }.exhaustive
    }

    private fun validate(): Boolean {
        return if (state.title.isBlank()) {
            state =
                state.copy(error = application.getString(R.string.text_message_error_title_is_blank))
            false
        } else true
    }

    private fun add() {
        viewModelScope.launch {
            taskRepository.saveTask(
                Task(
                    id = 0,
                    title = state.title,
                    description = state.description,
                    dueDate = state.dueDate
                )
            )
            state = state.copy(isSuccess = true)
        }
    }

    private fun edit() {}
}