package com.example.nelsonfinalyearproject.util


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Monday::class], version = 1)
abstract class MondayDatabase: RoomDatabase() {
    abstract fun mondayDao(): MondayDao
}

class MDB(context: Context) {

    val mdb = Room.databaseBuilder(
        context.applicationContext,
        MondayDatabase ::class.java, "nelsonfinalyear.monday"
    ).allowMainThreadQueries().build()
}

