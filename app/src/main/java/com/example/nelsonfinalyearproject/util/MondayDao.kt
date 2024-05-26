package com.example.nelsonfinalyearproject.util

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MondayDao{

    @Insert
    fun create(student: Monday)

    @Query("SELECT * FROM Monday")
    fun getAll(): List<Monday>

    @Query("SELECT * FROM Monday WHERE id = :id")
    fun get(id: String): Monday

    @Update
    fun update(student: Monday)

    @Delete
    fun delete(student: Monday)

}