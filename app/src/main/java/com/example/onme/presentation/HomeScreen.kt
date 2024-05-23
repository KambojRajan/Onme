package com.example.onme.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Delete
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.onme.R
import com.example.onme.data.Note
import com.example.onme.data.NotesStates
import com.example.onme.data.events.NotesEvent
import io.getstream.sketchbook.Sketchbook
import io.getstream.sketchbook.rememberSketchbookController
import java.util.Date
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state:NotesStates,
    onEvent:(NotesEvent) -> Unit,
    navHostController: NavHostController
) {
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val backPressed = rememberSaveable { mutableStateOf(false) }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME && backPressed.value) {
                onEvent(NotesEvent.SaveNote)
                backPressed.value = false
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    BackHandler {
        backPressed.value = true
        navHostController.popBackStack()
    }

    Scaffold(
        modifier = Modifier
            .padding(10.dp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navHostController.navigate(NavTabs.create.name)
                },
                containerColor = colorResource(R.color.teal_700)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Create",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
        },
        topBar = {
            TextField(
                value = "",
                placeholder = { Text(text = "Search...", color = colorResource(id = R.color.neutral_400)) },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = colorResource(id = R.color.neutral_200),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                ),
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth().clip(RoundedCornerShape(50.dp)),
            )
        }

    ) {padding->

        Column {
            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn(
                contentPadding = padding,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ){
                items(state.Notes){note->
                    if(state.DeleteNoteOpen){
                        DeleteNoteDialog(onEvent = onEvent, note = note)
                    }
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(15.dp))
                            .background(
                                color = colorResource(R.color.teal_700)
                            )
                            .clickable {
                                navHostController.navigate(NavTabs.create.name)
                                onEvent(NotesEvent.SetTitle(note.title))
                                onEvent(NotesEvent.SetDescription(note.description))
                                onEvent(NotesEvent.SetId(note.id))
                            }
                            .padding(30.dp)
                    ){
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = note.title,
                                fontSize = 20.sp,
                                color=Color.White,
                            )
                            Text(
                                text=note.description,
                                fontSize = 12.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color= colorResource(R.color.linen)
                            )
                        }
                        IconButton(
                            onClick = {
                                onEvent(NotesEvent.showDeleteDialog)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Delete,
                                contentDescription = "Delete notes",
                                tint = colorResource(R.color.rose_500)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val dummyNotes = listOf(
        Note(
            id = 1,
            title = "Sample Note 1",
            description = "This is the description for sample note 1  af,ef nssmgn lkdnb m ,bn dnsb, snd ndgb sbn",
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

    val dummyState = NotesStates(
        Notes = dummyNotes,
        title = "",
        description = "",
        isAddingNote = false
    )

    HomeScreen(
        state = dummyState,
        onEvent = {},
        rememberNavController()
    )
}