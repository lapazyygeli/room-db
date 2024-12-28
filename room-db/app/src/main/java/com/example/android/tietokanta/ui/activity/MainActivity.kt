package com.example.android.tietokanta.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuProvider
import androidx.core.view.children
import com.example.android.tietokanta.databinding.ActivityMainBinding

/**
 * Main activity for the application. Application starting point.
 * Handles the main user interface and initializes view binding.
 */
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Is view visible? At the moment _binding is null"
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}