package com.example.android.tietokanta.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.android.tietokanta.data.model.Book

/**
 * Abstract class representing the Room database for storing book data.
 * Includes the Book entity and version information.
 * Utilizes type converters for complex data types.
 */
@Database(entities = [ Book::class ], version = 2)
@TypeConverters(BookTypeConverters::class)
abstract class BookDataBase: RoomDatabase() {
    abstract fun bookDAO(): BookDao
}

val migration1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE Book ADD COLUMN photoFileName TEXT")
    }
}