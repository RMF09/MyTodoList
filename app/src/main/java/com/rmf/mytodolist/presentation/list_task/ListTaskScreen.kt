package com.rmf.mytodolist.presentation.list_task

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rmf.mytodolist.R
import com.rmf.mytodolist.domain.model.Task
import com.rmf.mytodolist.presentation.destinations.AddEditTaskScreenDestination
import com.rmf.mytodolist.presentation.destinations.DetailTaskScreenDestination
import com.rmf.mytodolist.presentation.detail_task.DueDateText

@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun ListTaskScreen(
    navigator: DestinationsNavigator,
    viewModel: ListTaskViewModel = hiltViewModel()
) {

    val listTask by viewModel.task.collectAsState(initial = emptyList())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navigator.navigate(AddEditTaskScreenDestination()) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            item {
                Text(
                    text = "My Todo List",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                )
            }
            item {
                Spacer(modifier = Modifier.height(6.dp))
            }
            if (listTask.isEmpty())
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = stringResource(id = R.string.text_empty_list))
                        Text(
                            text = stringResource(id = R.string.text_empty_list_description),
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                }
            items(listTask) { item ->
                ItemTask(
                    task = item,
                    onClick = { task ->
                        navigator.navigate(
                            DetailTaskScreenDestination(task = task)
                        )
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemTask(modifier: Modifier = Modifier, task: Task, onClick: (Task) -> Unit) {
    Card(onClick = { onClick(task) }, modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 12.dp)
        ) {
            Text(text = task.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(6.dp))
            if (task.displayDueDate.isNotBlank())
                Box(modifier = Modifier.fillMaxWidth()) {
                    DueDateText(
                        text = task.displayDueDate, modifier = Modifier.align(
                            Alignment.CenterEnd
                        )
                    )
                }
        }
    }
}