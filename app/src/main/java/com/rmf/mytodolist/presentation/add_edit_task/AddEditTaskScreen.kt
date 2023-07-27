package com.rmf.mytodolist.presentation.add_edit_task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.maxkeppeker.sheets.core.models.base.SheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.ramcosta.composedestinations.annotation.Destination
import com.rmf.mytodolist.R
import com.rmf.mytodolist.domain.model.Task

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun AddEditTaskScreen(
    isEditMode: Boolean = false,
    task: Task? = null,
    viewModel: AddEditTaskViewModel = hiltViewModel()
) {
    val state = viewModel.state


    val stateCalendar = remember { SheetState() }


    CalendarDialog(state = stateCalendar,
        config = CalendarConfig(
            yearSelection = true,
            monthSelection = true,
            style = CalendarStyle.MONTH,
        ), selection = CalendarSelection.Date(
            onSelectDate = {
                viewModel.onEvent(AddEditTaskUIEvent.OnChangeDueDate(it))
            }
        )
    )

    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            value = state.title,
            label = {
                Text(text = stringResource(id = R.string.task_title))
            },
            placeholder = {
                Text(text = stringResource(id = R.string.placeholder_task_title))
            },
            onValueChange = { value ->
                viewModel.onEvent(AddEditTaskUIEvent.OnChangeTitle(value))
            }
        )
        TextField(
            value = state.description,
            label = {
                Text(text = stringResource(id = R.string.task_decription))
            },
            placeholder = {
                Text(text = stringResource(id = R.string.placeholder_task_decription))
            },
            onValueChange = { value ->
                viewModel.onEvent(AddEditTaskUIEvent.OnChangeDescription(value))
            }
        )
    }
}