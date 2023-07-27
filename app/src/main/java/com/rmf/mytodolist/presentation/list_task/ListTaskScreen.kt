package com.rmf.mytodolist.presentation.list_task

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rmf.mytodolist.domain.model.Task
import com.rmf.mytodolist.presentation.destinations.AddEditTaskScreenDestination
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun ListTaskScreen(
    navigator: DestinationsNavigator
) {

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
            items(20) { item ->
                ItemTask(
                    task = Task(
                        title = "Title $item",
                        description = "Description $item",
                        dueDate = LocalDate.now()
                    )
                )
            }
        }
    }
}

@Composable
fun ItemTask(modifier: Modifier = Modifier, task: Task) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 12.dp)
        ) {
            Text(text = task.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = task.description, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = task.displayDueDate,
                fontSize = 12.sp,
                color = Color.Red,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}