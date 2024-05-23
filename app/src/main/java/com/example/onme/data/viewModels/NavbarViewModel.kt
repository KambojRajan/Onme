package com.example.onme.data.viewModels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.onme.presentation.NavTabs

class NavbarViewModel:ViewModel() {
    var selectedTab by mutableStateOf(NavTabs.home.name)
        private set

    fun updateSelectedTab(tab:String){
        this.selectedTab = tab
    }
}