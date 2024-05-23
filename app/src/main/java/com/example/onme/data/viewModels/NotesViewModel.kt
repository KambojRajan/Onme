package com.example.onme.data.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onme.data.Note
import com.example.onme.data.NotesStates
import com.example.onme.data.daos.Notes
import com.example.onme.data.events.NotesEvent
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date


class NotesViewModel(
    private val dao: Notes,
) : ViewModel() {
    private val _state = MutableStateFlow(NotesStates())
    private val _notes = dao.getNotesOrderedByDate()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val statex = combine(_state, _notes) { state, noteList ->
        state.copy(Notes = noteList)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _state.value)

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    dao.deleteNote(event.note)
                }
            }
            NotesEvent.HideDialog -> {
                _state.update { it.copy(isAddingNote = false) }
            }
            NotesEvent.SaveNote -> {
                val title = _state.value.title
                val description = _state.value.description
                val updatedAt = Date()
                if (title.isEmpty() || description.isEmpty() ) {
                    Log.d("Tag","title => $title descriptio=> $description")
                    return
                }

                val note = Note(
                    title = title,
                    description = description,
                    updatedAt = updatedAt.toString(),
                    createdAt = updatedAt.toString()
                )
                Log.d("tag","id of this note is ${note.id}")
               if(note.id == null){
                   viewModelScope.launch {
                       dao.insert(note)
                   }
               }else{
                   viewModelScope.launch {
                       dao.update(note)
                   }
               }
                _state.update {
                    it.copy(
                        isAddingNote = false,
                        title = "",
                        description = ""
                    )
                }
            }

            is NotesEvent.SetDescription -> {
                _state.update { it.copy(description = event.description) }
            }
            is NotesEvent.SetTitle -> {
                _state.update { it.copy(title = event.title) }
            }
            NotesEvent.ShowDialog -> {
                _state.update { it.copy(isAddingNote = true) }
            }

            NotesEvent.hideDeleteDialog ->{
                _state.update {
                    it.copy(DeleteNoteOpen = false)
                }
            }
            NotesEvent.showDeleteDialog -> {
                _state.update {
                    it.copy(DeleteNoteOpen = true)
                }
            }

            is NotesEvent.SetId -> {
                _state.update {
                    it.copy(
                        Id = event.id
                    )
                }
            }

            NotesEvent.showSearchDisplay -> {
                _state.update{
                    it.copy(

                    )
                }
            }
        }
    }
}
