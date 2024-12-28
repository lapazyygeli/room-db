package com.example.android.tietokanta.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.android.tietokanta.utils.CameraMenuProvider
import com.example.android.tietokanta.data.model.Book
import com.example.android.tietokanta.data.repository.BookRepository
import java.util.Date
import java.util.UUID

/**
 * ViewModel for handling the input operations related to books in the application.
 * Manages the addition and removal of books in the repository and handles input fields.
 * Also integrates with the camera functionality through the CameraMenuProvider.
 */
class InputViewModel: ViewModel() {

    var menuProvider: CameraMenuProvider? = null
    private val bookRepository = BookRepository.getInstance()
    private var num: Int = 0
    private var title: String = ""
    private var edition: Int = 0
    private var date: Date = Date()
    var photoName: String = ""
    var book = Book(
        UUID.randomUUID(),
        num,
        title,
        edition,
        date,
        isRead = false
    )

    suspend fun addBook(book: Book) {
        if (book.title.isNotEmpty() &&
            //book.num != 0 &&
            book.edition != 0 &&
            book.date != date) {
            bookRepository.addBook(book)
        }
        // if book.date != date // ei välttämättä toimi, jos halutaan sama päivä
        // paitsi jos book.date !== date
        // In future this could be handled better, for example
        // we could give feedback to user that addBook event wasn't successful
    }

    suspend fun removeNthBook(nth: Int) {
        bookRepository.removeNthBook(nth)
    }

    fun resetInputProperties() {
        // Nyt ei tosiaan laita mitään, vaikka olis valuet
        // inputti kentässä. Tätä kannattaa tarkastella.
        num = 0
        title = ""
        edition = 0
        date = Date()
        photoName = ""
        book = Book(
            UUID.randomUUID(),
            num,
            title,
            edition,
            date,
            isRead = false
        )
    }
}