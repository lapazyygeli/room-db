package com.example.android.tietokanta.data.database

import androidx.room.TypeConverter
import java.util.Date

/**
 * Type converters for the Room database to handle custom data types.
 * Converts between Date objects and their Long representation for storage in the database.
 */
class BookTypeConverters {
    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun toDate(millis: Long): Date {
        return Date(millis)
    }
}