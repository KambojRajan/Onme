package com.example.onme.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.onme.R
import com.example.onme.data.NotesStates
import com.example.onme.data.events.NotesEvent
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScreen(state: NotesStates, onEvent: (NotesEvent) -> Unit,navHostController: NavHostController) {
    val date = Date()
    val dateFormat = SimpleDateFormat("HH:mm,MMMM dd", Locale.getDefault())
    val formattedDate = dateFormat.format(date)

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(NotesEvent.ShowDialog)
                },
                containerColor = colorResource(R.color.teal_700)
            ) {
                Icon(
                    imageVector = Icons.Default.Create,
                    contentDescription = "Draw",
                    tint = Color.White
                )
            }
        },
        topBar = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .background(
                        color = Color.White
                    )
            ) {
                IconButton(
                    onClick = {
                        onEvent(NotesEvent.SaveNote)
                        navHostController.navigate(NavTabs.home.name)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Go back"
                    )
                }
                IconButton(
                    onClick = {
                        onEvent(NotesEvent.SaveNote)
                        navHostController.navigate(NavTabs.home.name)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Done"
                    )
                }
            }
        },
        modifier = Modifier.background(color = Color.Blue),
        containerColor = Color.White
    ) { padding ->
        if (state.isAddingNote) {
            AddNotesDialog(onEvent = onEvent)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(color=Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TextField(
                value = state.title,
                onValueChange = {
                    onEvent(NotesEvent.SetTitle(it))
                },
                placeholder = {
                    Text(text = "Title", color = colorResource(id = R.color.neutral_400))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                )
            )
            Text(text = formattedDate, fontSize = 10.sp)
            TextField(
                value = state.description,
                onValueChange = {
                    onEvent(NotesEvent.SetDescription(it))
                },
                placeholder = {
                    Text(text = "Description", color = colorResource(id = R.color.neutral_400))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Gray)
                    .weight(1f),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                )
            )
        }
    }
}

@Preview
@Composable
fun NotesScreenPreview() {
    CreateScreen(
        state = NotesStates(
            title = "",
            description = ""
        ),
        onEvent = {},
        rememberNavController()
    )
}
