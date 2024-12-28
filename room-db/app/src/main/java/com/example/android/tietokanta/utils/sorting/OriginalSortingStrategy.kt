package com.example.android.tietokanta.utils.sorting

import com.example.android.tietokanta.data.model.Book

class OriginalSortingStrategy : SortingStrategy {
    override fun sort(books: List<Book>): List<Book> {
        return books.sortedBy { it.objectCreationDate.time }
    }

}
