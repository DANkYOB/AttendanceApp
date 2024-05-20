package com.example.nelsonfinalyearproject.util

import android.content.Context

class NoteSaver(context: Context) {

    private val db = DB(context)

    fun saveNote(note: Note) {
        db.db.noteDao().create(note)
    }

    fun getAllNotes(): List<Note> {
        return db.db.noteDao().getAll()
    }

    fun deleteNote(note: Note) {
        db.db.noteDao().delete(note)
    }

}