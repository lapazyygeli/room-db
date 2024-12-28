package com.example.android.tietokanta.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.tietokanta.data.model.Book
import com.example.android.tietokanta.databinding.ListItemBookBinding
import java.util.UUID

/**
 * Adapter class for displaying a list of books in a RecyclerView.
 * Binds book data to the view and handles item click events.
 */
class BookListAdapter(
    private val books: List<Book>,
    private val onBookClicked: (bookId: UUID) -> Unit
): RecyclerView.Adapter<BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBookBinding.inflate(inflater, parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(books[position], onBookClicked)
    }

    override fun getItemCount() = books.size
}