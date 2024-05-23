package com.example.onme.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.onme.data.daos.Notes

@Database(
    entities = [Note::class],
    version=1
)
abstract  class OnmeDatabase:RoomDatabase() {
    abstract val dao:Notes
}