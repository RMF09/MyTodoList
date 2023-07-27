package com.rmf.mytodolist.presentation.add_edit_task

import java.time.LocalDate

sealed class AddEditTaskUIEvent {
    data class OnChangeTitle(val value: String) : AddEditTaskUIEvent()
    data class OnChangeDescription(val value: String) : AddEditTaskUIEvent()
    data class OnChangeDueDate(val value: LocalDate) : AddEditTaskUIEvent()
}