package com.example.android.tietokanta.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.tietokanta.data.model.Book
import com.example.android.tietokanta.data.repository.BookRepository
import com.example.android.tietokanta.utils.sorting.OriginalSortingStrategy
import com.example.android.tietokanta.utils.sorting.SortingStrategy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "CrimeListViewModel"

// Lyhyesti: suspendin pointti se, ettei raskaat operaatiot blockaa
// (UI/Main-)threadii. ViewModelScopella on Dispatchers.Main. eli
// koruttu luodaan main-threadiin. Threadejä on siis useampia, joille
// koruttuja voiadan luoda. Suspend ei estä siinä tietyssä threadissä
// sitä päämenoo, mikä on menos.

// Jos tähän lois 2 viewModelScopee, ni sillo ne menis rinnakkain.
// Paitsi suspend edelleen blockaa. Itse asiassa kaikki tuolla
// launchin sisällä on blockissa.
/**
 * ViewModel for managing and providing access to a list of books.
 *
 * This ViewModel interacts with [BookRepository] to fetch and update the list of books asynchronously.
 * It also supports sorting functionality based on different [SortingStrategy] implementations.
 *
 * @property bookRepository Instance of [BookRepository] used for fetching books from the data layer.
 * @property sortingStrategy Current sorting strategy applied to the list of books.
 * @property _books MutableStateFlow holding the list of books internally.
 * @property books Immutable StateFlow exposing the list of books to external components.
 */
class BookListViewModel: ViewModel() {

    private val bookRepository = BookRepository.getInstance()
    private var sortingStrategy: SortingStrategy = OriginalSortingStrategy() // tää pitää tallentaa, ku lopetetaan koko ohjelma
    private val _books: MutableStateFlow<List<Book>> = MutableStateFlow(emptyList())
    val books: StateFlow<List<Book>>
        get() = _books.asStateFlow()

    init {
        viewModelScope.launch {
            bookRepository.getBooks().collect { bookList ->
                _books.value = bookList
            }
            if (sortingStrategy !is OriginalSortingStrategy) {
                sortBooksBy(sortingStrategy)
            }
        }
    }

    /**
     * Sorts books by sorting strategy and assign them to the flow. So not only sortin' here,
     * but also creating a new sorted list of books
     */
    fun sortBooksBy(sortingStrategy: SortingStrategy) {
        // tästä supsend fun? onko pitkä prosessi, aiheuttaako lagia?
        this.sortingStrategy = sortingStrategy
        _books.value = sortingStrategy.sort(_books.value)
    }
}