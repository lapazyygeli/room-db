package com.example.android.tietokanta.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.android.tietokanta.utils.CameraMenuProvider
import com.example.android.tietokanta.ui.viewmodel.InputViewModel
import com.example.android.tietokanta.R
import com.example.android.tietokanta.databinding.FragmentInputBinding
import com.example.android.tietokanta.ui.viewmodel.UiViewModel
import kotlinx.coroutines.launch
import java.util.Date

/**
 * Fragment responsible for inputting book details and handling user interactions.
 */
class InputFragment : Fragment() {
    /*
     *  Käsitykseni:
     *  - fragmenteille toi attach tapahtuu aina automaattisesti,
     *    jossain on joku implmentation sille, oisko FragmentManager
     *
     *  - jos laittais true fragmentille, nii attach tapahtuis 2 kertaa --> virhe
     */

    private val inputViewModel: InputViewModel by viewModels()
    private val uiViewModel: UiViewModel by viewModels()
    private var _binding: FragmentInputBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }
    private val takePhoto = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { didTakePhoto: Boolean ->
        if (didTakePhoto && inputViewModel.photoName.isNotEmpty()) {
            inputViewModel.book = inputViewModel.book.copy(photoFileName = inputViewModel.photoName)
            binding.photoSuccess.text = getString(R.string.a_photo_has_been_taken_successfully)
        }
    }

    /**
     *  Käsitykseni:
     *  - tässä container=FragmentContainerView
     *  - koska voidaan luoda non-graphical fragments, niin tässä voidaan return null
     *
     *  RATKAISU:
     *  - In Android, when you inflate a layout in a Fragment's onCreateView() method,
     *    the layout is automatically attached to the root ViewGroup (container)
     *    that you specify. You don't need to manually attach it.
     *    It gets attached automatically when you return it from that method.
     *  - ts. jokaselle viewlle, joka palautetaan tapahtuu attach, kun kyse on
     *    fragmentin onCreateView-metodista.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        /*Siis ei kytketä LinearLayoutia containeriin eli FragmentContainerViewhun vielä
        * tässä vaiheessa. Nyt vain inflatointi. Sitten tapahtuu return, jossa me
        * returnataan LinearLayout ja sen lapset. Sitten tapahtuu automaattinen attach.
        * LinearLayout kytkeytyy FragmentContainerView:n lapseksi.*/
        //return inflater.inflate(R.layout.fragment_input, container, false)
        _binding = FragmentInputBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            bookNumber.doOnTextChanged { text, _, _, _ ->
                if (text.toString().isNotEmpty()) {
                    inputViewModel.book = inputViewModel.book.copy(num = text.toString().toInt())
                }
            }
            bookName.doOnTextChanged { text, _, _, _ ->
                inputViewModel.book = inputViewModel.book.copy(title = text.toString())
            }
            bookEdition.doOnTextChanged { text, _, _, _ ->
                if (text.toString().isNotEmpty()) {
                    inputViewModel.book = inputViewModel.book.copy(edition = text.toString().toInt())
                }
            }
            purchaseDate.text = uiViewModel.dateText
            purchaseDate.setOnClickListener {
                val args = Bundle().apply {
                    putSerializable(
                        DatePickerFragmentWithoutNavLib.BOOK_DATE,
                        inputViewModel.book.date
                    )
                    putString(
                        DatePickerFragmentWithoutNavLib.REQUEST_KEY_DATE,
                        DatePickerFragmentWithoutNavLib.REQUEST_KEY_DATE_INPUT_FRAG
                    )
                }
                val datePickerFragmentWithoutNavLib = DatePickerFragmentWithoutNavLib()
                datePickerFragmentWithoutNavLib.arguments = args
                datePickerFragmentWithoutNavLib.show(
                    childFragmentManager,
                    DatePickerFragmentWithoutNavLib.DATE_PICKER_FRAG_TAG
                )
            }

            addBtn.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    inputViewModel.addBook(inputViewModel.book)
                    inputViewModel.resetInputProperties()
                    resetInputUiText()
                }
            }
            removeBtn.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    inputViewModel.removeNthBook(0)
                }
            }
            sortBtn.setOnClickListener {
                FilterBottomSheetFragment().show(
                    childFragmentManager,
                    FilterBottomSheetFragment.TAG
                )
            }
        }

        //DatePicker things here
        childFragmentManager.setFragmentResultListener(
            DatePickerFragmentWithoutNavLib.REQUEST_KEY_DATE_INPUT_FRAG,
            this
        ) { _, bundle ->
            val dateFromPicker = bundle.getSerializable(
                DatePickerFragmentWithoutNavLib.BUNDLE_KEY_DATE
            ) as Date
            inputViewModel.book = inputViewModel.book.copy(date = dateFromPicker)
            binding.purchaseDate.text = dateFromPicker.toString()
            uiViewModel.dateText = dateFromPicker.toString()
        }

        // Menu things here
        val menuProvider = CameraMenuProvider(
            inputViewModel,
            requireActivity(),
            requireContext(),
            takePhoto
        )
        inputViewModel. menuProvider = menuProvider
        requireActivity().addMenuProvider(menuProvider, requireActivity())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        inputViewModel.menuProvider?.let { requireActivity().removeMenuProvider(it) }
        _binding = null
    }

    private fun resetInputUiText() {
        binding.apply {
            bookNumber.text = null
            bookName.text = null
            bookEdition.text = null
            purchaseDate.text = requireContext().getString(R.string.input_date)
            photoSuccess.text = null
        }
    }
}