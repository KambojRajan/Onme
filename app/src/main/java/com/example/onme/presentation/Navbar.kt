package com.example.onme.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.onme.R
import com.example.onme.data.NotesStates
import com.example.onme.data.events.NotesEvent
import com.example.onme.data.viewModels.NavbarViewModel

enum class NavTabs {
    home,
    create
}

@Composable
fun BottomNavigationBar(state: NotesStates, onEvent: (NotesEvent) -> Unit) {
    val navHostController = rememberNavController()

    Scaffold(
        modifier=Modifier.background(color=Color.White),
        containerColor = Color.White
    ) { innerPadding ->
        NavHost(
            navController = navHostController,
            startDestination = NavTabs.home.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavTabs.home.name) {
                HomeScreen(state = state, onEvent = onEvent, navHostController = navHostController)
            }
            composable(NavTabs.create.name) {
                CreateScreen(state = state, onEvent = onEvent, navHostController = navHostController)
            }
        }
    }
}


@Preview
@Composable
fun BottomNavPrev() {
    BottomNavigationBar(
        state = NotesStates(
            title = "",
            description = ""
        ),
        onEvent = {}
    )
}