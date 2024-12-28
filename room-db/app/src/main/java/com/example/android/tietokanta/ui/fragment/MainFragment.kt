package com.example.android.tietokanta.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import com.example.android.tietokanta.databinding.FragmentBookDetailBinding
import com.example.android.tietokanta.databinding.FragmentMainBinding
import java.util.Date

/**
 * A simple Fragment subclass.
 *
 * This fragment is responsible for displaying the main content of the application.
 * It inflates the layout defined by FragmentMainBinding and handles its lifecycle.
 * Instantiates InputFragment and BookListFragment.
 */
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}