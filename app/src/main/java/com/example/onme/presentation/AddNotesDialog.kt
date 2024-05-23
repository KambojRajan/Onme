package com.example.onme.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.onme.data.NotesStates
import com.example.onme.data.events.NotesEvent
import io.getstream.sketchbook.Sketchbook
import io.getstream.sketchbook.rememberSketchbookController


@Composable
fun AddNotesDialog(
    onEvent: (NotesEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier=modifier,
        onDismissRequest = { onEvent(NotesEvent.HideDialog) },
        title = { Text(text = "Draw something") },
        text = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.LightGray)
                    ) {
                        DrawingCanvas()
                    }
                }
        },
        confirmButton = {
            Button(
                onClick = { onEvent(NotesEvent.SaveNote) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Create")
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onEvent(NotesEvent.HideDialog) }
            ) {
                Text(text = "Cancel")
            }
        }
    )
}

@Composable
fun DrawingCanvas() {
    val path = remember { mutableStateOf(Path()) }

    Canvas(modifier = Modifier
        .fillMaxSize()
        .pointerInput(Unit) {
            detectDragGestures { change, dragAmount ->
                path.value.lineTo(change.position.x, change.position.y)
                change.consume()
            }
        }) {
        drawPath(path.value, Color.Black, style = Stroke(width = 4.dp.toPx()))
    }
}
