package com.rmf.mytodolist.presentation.list_task

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
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
import com.rmf.mytodolist.ui.composable.ErrorDialog

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun ListTaskScreen(
    navigator: DestinationsNavigator,
    viewModel: ListTaskViewModel = hiltViewModel()
) {

    val listTask by viewModel.task.collectAsState(initial = emptyList())
    val errorMessage by viewModel.error.collectAsState(null)

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navigator.navigate(AddEditTaskScreenDestination()) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
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
            items(items = listTask, key = { taskItem -> taskItem.id }) { item ->
                val currentItem by rememberUpdatedState(newValue = item)
                val dismissState = rememberDismissState(confirmValueChange = {
                    viewModel.removeTask(currentItem)
                    true
                })

                SwipeToDismiss(
                    modifier = Modifier.animateItemPlacement(),
                    state = dismissState,
                    directions = setOf(DismissDirection.EndToStart),
                    background = {
                        SwipeDismissBackground(dismissState)
                    },
                    dismissContent = {
                        ItemTask(
                            task = item,
                            onClick = { task ->
                                navigator.navigate(
                                    DetailTaskScreenDestination(task = task)
                                )
                            },
                            onCheckedTask = { updatedTask ->
                                viewModel.checkedTask(updatedTask)
                            }
                        )
                    })

            }
        }
    }
    errorMessage?.let { message ->
        ErrorDialog(title = stringResource(id = R.string.title_error), description = message) {
            viewModel.dismissDialog()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeDismissBackground(dismissState: DismissState) {
    val color by animateColorAsState(
        targetValue = when (dismissState.targetValue) {
            DismissValue.Default -> Color.Transparent
            DismissValue.DismissedToEnd -> Color.Red.copy(alpha = 0.16f)
            DismissValue.DismissedToStart -> Color.Red.copy(alpha = 0.16f)
        }
    )
    Box(
        modifier = Modifier
            .background(color = color)
            .fillMaxSize(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.54f)
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemTask(
    modifier: Modifier = Modifier,
    task: Task,
    onClick: (Task) -> Unit,
    onCheckedTask: (Task) -> Unit
) {
    Card(onClick = { onClick(task) }, modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f, true),
                    text = task.title, style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Checkbox(
                    checked = task.isCompleted,
                    onCheckedChange = { onCheckedTask(task) }
                )
            }
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