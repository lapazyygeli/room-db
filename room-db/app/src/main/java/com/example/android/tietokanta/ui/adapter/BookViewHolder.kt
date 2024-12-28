package com.example.android.tietokanta.ui.adapter

import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.android.tietokanta.R
import com.example.android.tietokanta.data.model.Book
import com.example.android.tietokanta.data.repository.BookRepository
import com.example.android.tietokanta.databinding.ListItemBookBinding
import kotlinx.coroutines.launch
import java.util.UUID

/**
 * ViewHolder class for displaying individual book items in a RecyclerView.
 * Binds book data to the view and handles item click and delete events.
 */
class BookViewHolder(
    private val binding: ListItemBookBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(book: Book, onBookClicked: (crimeId: UUID) -> Unit) {
        binding.apply {
            bookNum.text = itemView
                .context
                .getString(R.string.book_num_placeholder_ordinal, book.num.toString())
            bookTitle.text = book.title
        }

        binding.root.setOnClickListener {
            onBookClicked(book.id)
        }

        binding.trashbin.setOnClickListener {view: View ->
            // Luultavasti saattaisi olla viisaampaa laittaa kirjan poisto metodi
            // dependency injektion kautta kuin luoda suoraan toiminnot tässä
            AlertDialog.Builder(binding.root.context)
                .setTitle(view.context.getString(R.string.delete_book))
                .setMessage(view.context.getString(R.string.are_u_sure_delete_book))
                .setPositiveButton(view.context.getString(R.string.yes)) { _, _ ->
                    view.findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
                        BookRepository.getInstance().removeBook(book)
                    }
                }
                .setNegativeButton(view.context.getString(R.string.no), null)
                .show()

        }

        binding.bookDone.visibility = if (book.isRead) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}