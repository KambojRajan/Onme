package com.example.onme.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.onme.R
import com.example.onme.data.Note
import com.example.onme.data.NotesStates
import com.example.onme.data.events.NotesEvent
import java.util.Date

@Composable
fun DeleteNoteDialog(
    onEvent: (NotesEvent) -> Unit,
    modifier: Modifier = Modifier,
    note: Note
) {
    AlertDialog(
        modifier = modifier
            .background(Color.White),
        containerColor = Color.White,
        onDismissRequest = { onEvent(NotesEvent.hideDeleteDialog) },
        title = { Text(text = "Are you sure you want to delete this note?") },
        text = { Text(text = "This action cannot be undone.",color= Color.Gray) },
        confirmButton = {
            Button(
                onClick = {
                    onEvent(NotesEvent.DeleteNote(note = note))
                    onEvent(NotesEvent.hideDeleteDialog)
                },
                modifier = Modifier.padding(end = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.rose_500)
                )
            ) {
                Text(text = "Delete")
            }
        },
        dismissButton = {
            Button(
                onClick = { onEvent(NotesEvent.hideDeleteDialog) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.antique_white)
                )
            ) {
                Text(text = "Cancel", color = Color.Black)
            }
        },
    )
}


@Preview
@Composable
fun PreviewE() {
    val dummyNotes = listOf(
        Note(
            id = 1,
            title = "Sample Note 1",
            description = "This is the description for sample note 1.",
            createdAt = Date().toString(),
            updatedAt = Date().toString()
        ),
        Note(
            id = 2,
            title = "Sample Note 2",
            description = "This is the description for sample note 2.",
            createdAt = Date().toString(),
            updatedAt = Date().toString()
        )
    )
    DeleteNoteDialog(onEvent = {}, note = dummyNotes[0])
}