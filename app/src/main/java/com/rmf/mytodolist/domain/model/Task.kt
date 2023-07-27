package com.rmf.mytodolist.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class Task(
    val title: String,
    val description: String,
    val dueDate: LocalDate,
) : Parcelable {
    val displayDueDate : String get(){
        return "${dueDate.dayOfMonth}-${dueDate.monthValue}-${dueDate.year}"
    }
}