package com.example.nelsonfinalyearproject.util

import android.content.Context

class MondaySaver(context: Context) {

    private val db = MDB(context)

    fun saveMonday(monday: Monday) {
        db.mdb.mondayDao().create(monday)
    }

    fun getAllMondays(): List<Monday> {
        return db.mdb.mondayDao().getAll()
    }

    fun deleteMonday(monday: Monday) {
        db.mdb.mondayDao().delete(monday)
    }

}