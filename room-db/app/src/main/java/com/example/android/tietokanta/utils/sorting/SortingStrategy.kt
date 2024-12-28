package com.example.android.tietokanta.utils.sorting

import com.example.android.tietokanta.data.model.Book

interface SortingStrategy {
    fun sort(books: List<Book>): List<Book>
}
