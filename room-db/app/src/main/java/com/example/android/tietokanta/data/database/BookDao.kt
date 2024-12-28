package com.example.android.tietokanta.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.android.tietokanta.data.model.Book
import kotlinx.coroutines.flow.Flow
import java.util.UUID

/**
 * Data Access Object (DAO) interface for accessing and managing book data in the database.
 * Provides methods to perform operations such as retrieving, adding, updating, and removing books.
 */
@Dao
interface BookDao {

    // Tähän luultavasti ordering lisäyspäivämäärän mukaisesti!!!
    @Query("SELECT * FROM book")
    fun getBooks(): Flow<List<Book>>

    @Query("SELECT * FROM book WHERE id=(:uuid)")
    suspend fun getBook(uuid: UUID): Book

    @Query("SELECT * FROM book ORDER BY objectCreationDate ASC LIMIT 1 OFFSET :nth")
    suspend fun getNthBookByTimePosition(nth: Int): Book?

    @Insert
    suspend fun addBook(book: Book)

    @Delete
    suspend fun removeBook(book: Book)

    @Update
    suspend fun updateBook(book: Book)
}