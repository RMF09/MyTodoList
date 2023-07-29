package com.rmf.mytodolist.presentation.add_edit_task

import java.time.LocalDate

data class AddEditTaskState(
    val title: String = "",
    val description: String = "",
    val dueDate: LocalDate? = null,
    val error: String? = null,
    val isSuccess: Boolean = false
) {
    val displayDueDate: String
        get() {
            return if (dueDate != null)
                "${dueDate.dayOfMonth}-${dueDate.monthValue}-${dueDate.year}"
            else
                ""
        }
}