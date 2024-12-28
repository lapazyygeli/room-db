package com.example.android.tietokanta.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.tietokanta.ui.viewmodel.BookListViewModel
import com.example.android.tietokanta.databinding.FragmentBookListBinding
import com.example.android.tietokanta.ui.adapter.BookListAdapter
import kotlinx.coroutines.launch

/**
 * Fragment that displays a list of books using a RecyclerView.
 * Allows navigation to the details of each book upon selection.
 */
class BookListFragment : Fragment() {

    // Lazyllä on getValue, joka on myös operator overloaded pisteellä.
    // Koska delegate, ja blvm.jotain, ni blvm antaa sen tolle lazylle
    // Tai ts. blvm: on sen getValue funktio, ja sit se delegoi sille.
    // --> Koska kyseessä on lazy, ni sen takii ei tarvii alustaa
    private val bookListViewModel: BookListViewModel by activityViewModels()//viewModels()
    private var _binding: FragmentBookListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    /**
     * - https://developer.android.com/reference/kotlin/androidx/navigation/fragment/package-summary
     * - Toi o mesta, mistä löytyy toi findNavController. Se on siis extensio fragmentille.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookListBinding.inflate(inflater, container, false)
        binding.bookRecyclerView.layoutManager = LinearLayoutManager(context)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                bookListViewModel.books.collect { books ->
                    binding.bookRecyclerView.adapter = BookListAdapter(books) { crimeId ->
                        findNavController().navigate(
                            MainFragmentDirections.showBookDetail(
                                crimeId
                            )
                        )
                    }
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}