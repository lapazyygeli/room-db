package com.example.android.tietokanta.ui.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.android.tietokanta.databinding.FragmentFilterBottomSheetBinding
import com.example.android.tietokanta.utils.sorting.NameAscSortingStrategy
import com.example.android.tietokanta.utils.sorting.NameDescSortingStrategy
import com.example.android.tietokanta.utils.sorting.NumAscSortingStrategy
import com.example.android.tietokanta.utils.sorting.NumDescSortingStrategy
import com.example.android.tietokanta.utils.sorting.OriginalSortingStrategy
import com.example.android.tietokanta.ui.viewmodel.BookListViewModel
import com.example.android.tietokanta.ui.viewmodel.FilterViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * A BottomSheetDialogFragment that allows the user to apply sorting filters for the book list.
 * It provides options for sorting books alphabetically by name (ascending/descending) or by number
 * (ascending/descending), and applying or resetting the selected sorting strategy.
 */
class FilterBottomSheetFragment : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "FilterBottomSheetFragment"
    }

    private var _binding: FragmentFilterBottomSheetBinding? = null
    private val binding: FragmentFilterBottomSheetBinding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }
    private val viewModel: FilterViewModel by viewModels()
    private val bookListViewModel: BookListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBottomSheetBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            chipAlphabeticalSortAsc.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    viewModel.sortingStrategy = NameAscSortingStrategy()
                    //binding.chipNumberSortAsc.chec
                }
            }
            chipAlphabeticalSortDesc.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    viewModel.sortingStrategy = NameDescSortingStrategy()
                }
            }
            chipNumberSortAsc.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    viewModel.sortingStrategy = NumAscSortingStrategy()
                }
            }
            chipNumberSortDesc.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    viewModel.sortingStrategy = NumDescSortingStrategy()
                }
            }
            btnApply.setOnClickListener {
                dismiss()
            }
            btnResetSelection.setOnClickListener {
                viewModel.sortingStrategy = OriginalSortingStrategy()
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        bookListViewModel.sortBooksBy(viewModel.sortingStrategy)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}