package com.rmf.mytodolist.ui.composable

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun ErrorDialog(
    title: String,
    description: String,
    onConfirm: () -> Unit
) {
    AlertDialog(
        icon = {
            Icon(imageVector = Icons.Default.Warning, contentDescription = null)
        },
        title = {
            Text(text = title)
        },
        text = {
            Text(text = description)
        },
        onDismissRequest = { /*TODO*/ },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
            ) {
                Text(text = "Oke")
            }
        }
    )

}