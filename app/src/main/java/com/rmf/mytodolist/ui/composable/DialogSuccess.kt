package com.rmf.mytodolist.ui.composable

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun SuccessDialog(
    title: String,
    description: String,
    onConfirm: () -> Unit
) {
    AlertDialog(
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