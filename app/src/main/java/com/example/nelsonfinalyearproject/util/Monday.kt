package com.example.nelsonfinalyearproject.util

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Monday(
    @PrimaryKey
    val id: String,
    val title: String,
    val body: String
) : Parcelable