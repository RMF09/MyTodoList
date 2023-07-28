package com.rmf.mytodolist.presentation.add_edit_task

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.maxkeppeker.sheets.core.models.base.SheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rmf.mytodolist.R
import com.rmf.mytodolist.domain.model.Task

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun AddEditTaskScreen(
    isEditMode: Boolean = false,
    task: Task? = null,
    navigator: DestinationsNavigator,
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

    val title = if (isEditMode) R.string.title_edit_task else R.string.title_add_task
    val textButton = if (isEditMode) R.string.text_button_edit else R.string.text_button_add


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = title))
                },
                navigationIcon = {
                    IconButton(onClick = { navigator.navigateUp() }) {
                        Icon(imageVector = Icons.Default.ChevronLeft, contentDescription = null)
                    }
                }
            )
        },
        bottomBar = {
            Button(
                onClick = { }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(text = stringResource(id = textButton))
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
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
            TextField(
                enabled = false,
                value = state.displayDueDate,
                label = {
                    Text(text = stringResource(id = R.string.task_due_date))
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.placeholder_task_due_date))
                },
                onValueChange = {},
                colors = TextFieldDefaults.textFieldColors(
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                modifier = Modifier.clickable { stateCalendar.show() }
            )
        }
    }

}