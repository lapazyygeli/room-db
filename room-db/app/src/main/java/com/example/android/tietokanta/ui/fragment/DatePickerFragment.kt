package com.example.android.tietokanta.ui.fragment

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import java.util.Calendar
import java.util.GregorianCalendar

/**
 * Fragment that displays a DatePickerDialog for selecting a date.
 * Communicates the selected date back to the calling fragment using setFragmentResult.
 */
class DatePickerFragment: DialogFragment() {

    companion object {
        const val REQUEST_KEY_DATE_DETAIL_FRAG = "REQUEST_KEY_DATE_DETAIL_FRAG"
        const val BUNDLE_KEY_DATE = "BUNDLE_KEY_DATE"
    }

    private val args: DatePickerFragmentArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        calendar.time = args.bookDate
        val initialYear = calendar.get(Calendar.YEAR)
        val initialMonth = calendar.get(Calendar.MONTH)
        val initialDay = calendar.get(Calendar.DAY_OF_MONTH)

        val dateListener = OnDateSetListener { _, year, month, dayOfMonth ->
            Log.d("settii", "kuunneltu")
            val resultDate = GregorianCalendar(year, month, dayOfMonth).time
            setFragmentResult(args.requestKeyDate,
                bundleOf(BUNDLE_KEY_DATE to resultDate))
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