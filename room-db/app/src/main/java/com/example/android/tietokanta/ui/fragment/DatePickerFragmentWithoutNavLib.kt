package com.example.android.tietokanta.ui.fragment

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar

/**
 * A DialogFragment that displays a DatePickerDialog for selecting a date without using navigation library components.
 * Communicates the selected date back to the calling fragment using setFragmentResult.
 */
class DatePickerFragmentWithoutNavLib: DialogFragment() {

    companion object {
        const val REQUEST_KEY_DATE_INPUT_FRAG = "REQUEST_KEY_DATE_INPUT_FRAG"
        const val REQUEST_KEY_DATE = "REQUEST_KEY_DATE"
        const val BUNDLE_KEY_DATE = "BUNDLE_KEY_DATE"
        const val BOOK_DATE = "BOOK_DATE"
        const val DATE_PICKER_FRAG_TAG = "DATE_PICKER_FRAG_TAG"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()

        calendar.time = arguments?.getSerializable(BOOK_DATE) as Date
        val initialYear = calendar.get(Calendar.YEAR)
        val initialMonth = calendar.get(Calendar.MONTH)
        val initialDay = calendar.get(Calendar.DAY_OF_MONTH)

        val dateListener = OnDateSetListener { _, year, month, dayOfMonth ->
            val resultDate = GregorianCalendar(year, month, dayOfMonth).time
            val requestKey = arguments?.getString(REQUEST_KEY_DATE) ?: ""
            setFragmentResult(
                requestKey,
                bundleOf(BUNDLE_KEY_DATE to resultDate)
            )
        }

        return DatePickerDialog(
            requireContext(),
            dateListener,
            initialYear,
            initialMonth,
            initialDay
        )
    }
}