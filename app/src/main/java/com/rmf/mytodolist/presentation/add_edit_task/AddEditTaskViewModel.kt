package com.rmf.mytodolist.presentation.add_edit_task

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.rmf.mytodolist.domain.model.Task
import com.rmf.mytodolist.util.exhaustive
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val isEditMode = savedStateHandle.get<Boolean>("isEditMode") ?: false
    private val taskWantBeEdited = savedStateHandle.get<Task>("taskWantBeEdited")
    var state by mutableStateOf(AddEditTaskState())

    init {
        if (isEditMode) {
            taskWantBeEdited?.let { task ->
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
        }.exhaustive
    }

    private fun add() {}
}