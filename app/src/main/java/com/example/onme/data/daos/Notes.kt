package com.example.onme.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.onme.data.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface  Notes {

    @Insert
    suspend fun insert(Note:Note)

    @Upsert
    suspend fun update(Note:Note)

    @Delete
    suspend fun deleteNote(Note:Note)

    @Query("SELECT * FROM Note ORDER BY updatedAt DESC")
    fun getNotesOrderedByDate():Flow<List<Note>>

}