package com.rmf.mytodolist.presentation.detail_task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rmf.mytodolist.R
import com.rmf.mytodolist.domain.model.Task

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun DetailTaskScreen(
    navigator: DestinationsNavigator,
    task: Task
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = stringResource(id = R.string.title_detail_task))
            },
                navigationIcon = {
                    IconButton(onClick = { navigator.navigateUp() }) {
                        Icon(imageVector = Icons.Default.ChevronLeft, contentDescription = null)
                    }
                })
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                Text(text = task.description)
                Spacer(modifier = Modifier.height(16.dp))
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
}

@Composable
fun DueDateText(modifier: Modifier = Modifier, text: String) {
    Box(
        modifier = modifier
            .background(
                color = Color.Red.copy(alpha = 0.3f),
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}