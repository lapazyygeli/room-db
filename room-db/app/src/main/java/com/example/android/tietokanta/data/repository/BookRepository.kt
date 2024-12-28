package com.example.android.tietokanta.data.repository

import android.content.Context
import androidx.room.Room
import com.example.android.tietokanta.data.model.Book
import com.example.android.tietokanta.data.database.BookDataBase
import com.example.android.tietokanta.data.database.migration1_2
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.UUID

private const val DATABASE_NAME = "bookdb"

/**
 * Repository class for managing book data operations.
 * Provides methods to perform CRUD operations and handles database interactions.
 * This is a singleton class.
 */
class BookRepository @OptIn(DelicateCoroutinesApi::class)
private constructor(
    context: Context,
    private val coroutineScope: CoroutineScope = GlobalScope
) {

    private val database: BookDataBase = Room
        .databaseBuilder(
            context.applicationContext,
            BookDataBase::class.java,
            DATABASE_NAME
        )
        //.createFromAsset(DATABASE_NAME)
        //.fallbackToDestructiveMigration()
        .addMigrations(migration1_2)
        .build()

    fun getBooks(): Flow<List<Book>> = database.bookDAO().getBooks()
    suspend fun getBook(id: UUID): Book = database.bookDAO().getBook(id)
    suspend fun addBook(book: Book) = database.bookDAO().addBook(book)
    suspend fun removeBook(book: Book) = database.bookDAO().removeBook(book)

    // indexing from 0 to upwards
    suspend fun removeNthBook(nth: Int) {
        val nthBookToRemove = database.bookDAO().getNthBookByTimePosition(nth)
        nthBookToRemove?.let {
            database.bookDAO().removeBook(it)
        }
    }
    fun updateBook(book: Book) {
        coroutineScope.launch {
            database.bookDAO().updateBook(book)
        }
    }

    companion object {
        private var INSTANCE: BookRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = BookRepository(context)
            }
        }

        fun getInstance(): BookRepository {
            return INSTANCE ?:
            throw IllegalStateException("CrimeRepository must be initialized")
        }
    }
}