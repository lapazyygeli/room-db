package com.example.android.tietokanta.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.util.UUID

/**
 * Factory class for creating instances of BookDetailViewModel.
 *
 * @param bookId The unique identifier of the book for which the ViewModel is created.
 */
class BookDetailViewModelFactory(
    private val bookId: UUID
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return BookDetailViewModel(bookId) as T
    }
}