package com.rmf.mytodolist.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val dueDate: LocalDate? = null,
) : Parcelable {
    val displayDueDate: String
        get() {
            return if (dueDate != null)
                "${dueDate.dayOfMonth}-${dueDate.monthValue}-${dueDate.year}"
            else ""
        }
}