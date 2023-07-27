package com.rmf.mytodolist.presentation.add_edit_task

import java.time.LocalDate

data class AddEditTaskState(
    val title: String = "",
    val description: String = "",
    val dueDate: LocalDate? = null
)