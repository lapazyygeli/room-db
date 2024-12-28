package com.example.android.tietokanta.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.FileProvider
import androidx.core.view.MenuProvider
import com.example.android.tietokanta.R
import com.example.android.tietokanta.ui.viewmodel.InputViewModel
import java.io.File
import java.util.Date

/**
 * Provides menu handling for camera-related actions in an Android application.
 *
 * This class implements the [MenuProvider] interface to manage menu creation,
 * preparation, and item selection for camera actions within the application's UI.
 *
 * @property inputViewModel The ViewModel managing input data and state.
 * @property activity The hosting activity where the menu is displayed.
 * @property context The context of the application for accessing resources and file handling.
 * @property takePhoto The launcher for handling the capture photo intent result.
 */
class CameraMenuProvider(
    private val inputViewModel: InputViewModel,
    private val activity: Activity,
    private val context: Context,
    private val takePhoto: ActivityResultLauncher<Uri>
): MenuProvider {
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.fragment_input, menu)
    }

    override fun onPrepareMenu(menu: Menu) {
        val bookCamera: MenuItem = menu.findItem(R.id.new_photo)
        val captureImageIntent = takePhoto.contract.createIntent(
            context,
            Uri.parse("")
        )
        bookCamera.isEnabled = canResolveIntent(captureImageIntent)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.new_photo -> {
                inputViewModel.photoName = "IMG_${Date()}.JPG"
                val photoFile = File(context.applicationContext.filesDir,
                    inputViewModel.photoName)

                // Koko pitkä uri. Hyödyllinen myöhemmin mahdollisesti.
                val photoUri = FileProvider.getUriForFile(
                    context,
                    "com.example.android.tietokanta.fileprovider",
                    photoFile
                )

                takePhoto.launch(photoUri)
                true
            } else -> false
        }
    }

    private fun canResolveIntent(intent: Intent): Boolean {
        val packageManager: PackageManager = activity.packageManager
        val resolvedActivity: ResolveInfo? =
            packageManager.resolveActivity(
                intent,
                PackageManager.MATCH_DEFAULT_ONLY
            )
        return resolvedActivity != null
    }
}