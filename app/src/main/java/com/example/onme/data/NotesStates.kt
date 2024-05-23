package com.example.onme.data

data class NotesStates(
    val Notes:List<Note> = emptyList(),
    val title:String = "",
    val description:String = "",
    val Id:Int = 0,
    val author:String = "",
    val updatedAt:String = "",
    val created:String = "",
    val isAddingNote:Boolean = false,
    val DeleteNoteOpen:Boolean = false,
    val sortType:String = "updatedAt",
    val searchContent:String = "",
    val isSearching:Boolean = false
)
