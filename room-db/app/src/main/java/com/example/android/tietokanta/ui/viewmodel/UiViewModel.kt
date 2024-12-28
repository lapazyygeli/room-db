package com.example.android.tietokanta.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.android.tietokanta.R

class UiViewModel(
    private val application: Application
): AndroidViewModel(application) {

    var dateText: String = application.applicationContext.getString(R.string.input_date)
}
