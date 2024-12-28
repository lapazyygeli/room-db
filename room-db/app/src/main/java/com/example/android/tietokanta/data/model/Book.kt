package com.example.android.tietokanta.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.lang.System.currentTimeMillis
import java.util.Date
import java.util.UUID

/**
 * Data class representing a book entity in the Room database.
 * Includes information about the book such as its ID, number, title, edition,
 * date of addition, read status, creation date, and an optional photo file name.
 */
@Entity
data class Book(
    @PrimaryKey val id: UUID,
    val num: Int,
    val title: String,
    val edition: Int,
    val date: Date,
    val isRead: Boolean,
    val objectCreationDate: Date = Date(currentTimeMillis()),
    val photoFileName: String? = null
)
