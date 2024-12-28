package com.example.android.tietokanta.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.android.tietokanta.utils.sorting.OriginalSortingStrategy
import com.example.android.tietokanta.utils.sorting.SortingStrategy

/**
 * ViewModel for managing filter settings, including sorting strategy and UI state.
 *
 * This ViewModel handles the current sorting strategy applied to a list, and it also
 * manages the UI state related to filter settings (e.g., selected chip or button state).
 *
 * @property sortingStrategy The current sorting strategy applied to the list.
 * @property checkedChip Represents the UI state related to the selected filter chip or button.
 */
class FilterViewModel: ViewModel() {

    var sortingStrategy: SortingStrategy = OriginalSortingStrategy()
    //var checkedChip: Int = "rescourecInt?".toInt()
    // Täytyy pitää tallessa se napin ulkomuoto kanssa strategian lisäksi
    // Ja sitte se asetus täytyy myös tallentaa ragequitin kestävästi eli jos ohjelma sammutetaan
    // kokoaan

    override fun onCleared() {
        super.onCleared()
        Log.d("settii", "kalas")
    }
}