package com.example.onme.data.events

import com.example.onme.data.Note

sealed interface NotesEvent {
    object SaveNote:NotesEvent
    data class SetTitle(val title:String):NotesEvent
    data class SetId(val id:Int):NotesEvent
    data class SetDescription(val description: String):NotesEvent
    object ShowDialog:NotesEvent
    object HideDialog:NotesEvent
    object showDeleteDialog:NotesEvent
    object hideDeleteDialog:NotesEvent
    object showSearchDisplay:NotesEvent
    data class DeleteNote(val note:Note):NotesEvent
}