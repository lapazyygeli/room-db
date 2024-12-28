package com.example.android.tietokanta.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.doOnLayout
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.android.tietokanta.ui.viewmodel.BookDetailViewModel
import com.example.android.tietokanta.ui.viewmodel.BookDetailViewModelFactory
import com.example.android.tietokanta.data.model.Book
import com.example.android.tietokanta.databinding.FragmentBookDetailBinding
import com.example.android.tietokanta.utils.getScaledBitmap
import kotlinx.coroutines.launch
import java.io.File
import java.util.Date

private const val BACK_PRESSED_TOAST_TEXT = "Empty fields are not allowed: give each one some input!"

/**
 * Fragment for displaying and editing details of a book.
 * Allows the user to view and modify attributes such as book number, title, edition, date, and status.
 * Supports updating these attributes and displaying an image associated with the book.
 */
class BookDetailFragment : Fragment() {

    private var _binding: FragmentBookDetailBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val args: BookDetailFragmentArgs by navArgs()
    private val bookDetailViewModel: BookDetailViewModel by viewModels {
        BookDetailViewModelFactory(args.bookId)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.bookNumber.text.isEmpty() ||
                    binding.bookTitle.text.isEmpty() ||
                    binding.bookEdition.text.isEmpty()) {
                    Toast.makeText(
                        context,
                        BACK_PRESSED_TOAST_TEXT,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    findNavController().popBackStack()
                }
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(
            // Kun tän lifecyclen owner on ainakin started,
            // ni voidaan kutsuu sillo tää callback.
            // Ja sit jos poistuu myös oikein tää cb.
            this,
            callback
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            bookNumber.doOnTextChanged { text, _, _, _ ->
                val num: Int? = text.toString().toIntOrNull()
                if (num != null) {
                    bookDetailViewModel.updateBook { oldBook ->
                        oldBook.copy(num = num)
                    }
                }
            }
            bookTitle.doOnTextChanged { text, _, _, _ ->
                bookDetailViewModel.updateBook { oldBook ->
                    oldBook.copy(title = text.toString())
                }
            }
            bookEdition.doOnTextChanged { text, _, _, _ ->
                text?.toString()?.toIntOrNull()?.let { edition ->
                    bookDetailViewModel.updateBook { oldBook ->
                        oldBook.copy(edition = edition)
                    }
                }
            }
            bookSolved.setOnCheckedChangeListener { _, isChecked ->
                bookDetailViewModel.updateBook { oldBook ->
                    oldBook.copy(isRead = isChecked)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                bookDetailViewModel.book.collect { book ->
                    book?.let { updateUi(it) }
                }
            }
        }

        setFragmentResultListener(
            DatePickerFragment.REQUEST_KEY_DATE_DETAIL_FRAG
        ) { _, bundle ->
            val dateFromPicker = bundle.getSerializable(
                DatePickerFragment.BUNDLE_KEY_DATE
            ) as Date
            bookDetailViewModel.updateBook { oldBook ->
                oldBook.copy(date = dateFromPicker)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUi(book: Book) {
        binding.apply {
            if (bookNumber.text.toString() != book.num.toString()) {
                bookNumber.setText(book.num.toString())
            }

            // In the beginning bookTitle is empty, so this just
            // initializes this bookTitle edittext field with text
            if (bookTitle.text.toString() != book.title) {
                bookTitle.setText(book.title)
            }
            if (bookEdition.text.toString() != book.edition.toString()) {
                bookEdition.setText(book.edition.toString())
            }
            if (bookDate.text.toString() != book.date.toString()) {
                bookDate.text = book.date.toString()
                bookDate.setOnClickListener {
                    findNavController().navigate(
                        BookDetailFragmentDirections.selectDateFromDetailFrag(
                            book.date,
                            DatePickerFragment.REQUEST_KEY_DATE_DETAIL_FRAG
                        )
                    )
                }
            }
            if (bookSolved.isChecked != book.isRead) {
                bookSolved.isChecked = book.isRead
            }

            updatePhoto(book.photoFileName)

            bookPhoto.setOnClickListener {
                book.photoFileName?.let {
                    ImageDialogFragment(book.photoFileName).show(
                        childFragmentManager,
                        ImageDialogFragment.TAG
                    )
                }

            }
        }
    }

    private fun updatePhoto(photoFileName: String?) {
        if (binding.bookPhoto.tag != photoFileName) {
            val photoFile = photoFileName?.let {
                File(requireContext().applicationContext.filesDir, it)
            }

            if (photoFile?.exists() == true) {
                binding.bookPhoto.doOnLayout { view ->
                    val scaledBitmap = getScaledBitmap(
                        photoFile.path,
                        view.width,
                        view.height
                    )
                    binding.bookPhoto.setImageBitmap(scaledBitmap)
                    binding.bookPhoto.tag = photoFileName
                }
            } else {
                binding.bookPhoto.setImageBitmap(null)
                binding.bookPhoto.tag = null
            }
        }
    }
}