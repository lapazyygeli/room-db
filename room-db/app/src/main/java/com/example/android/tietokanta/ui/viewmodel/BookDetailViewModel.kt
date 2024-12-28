package com.example.android.tietokanta.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.tietokanta.data.model.Book
import com.example.android.tietokanta.data.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

/**
 * ViewModel for managing the details of a single book.
 *
 * @param bookId The unique identifier of the book whose details are managed by this ViewModel.
 */
class BookDetailViewModel(bookId: UUID): ViewModel() {
    private val bookRepository: BookRepository = BookRepository.getInstance()

    private val _book: MutableStateFlow<Book?> = MutableStateFlow(null)
    val book: StateFlow<Book?> = _book.asStateFlow()

    init {
        viewModelScope.launch {
            _book.value = bookRepository.getBook(bookId)
        }
    }

    fun updateBook(onUpdate: (Book) -> Book) {
        _book.update { oldBook ->
            oldBook?.let { onUpdate(it) } // oldBook?.let { oldBook -> onUpdate(oldBook) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        book.value?.let { bookRepository.updateBook(it) }
    }
}