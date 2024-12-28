package com.example.android.tietokanta.ui.fragment

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.android.tietokanta.databinding.ImageViewBinding
import java.io.File

/**
 * A DialogFragment that displays an image in a dialog using Glide for image loading.
 * The image file path is provided through the constructor.
 */
class ImageDialogFragment(
    private val imageName: String
): DialogFragment() {

    private var _binding: ImageViewBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    companion object {
        const val TAG = "ImageDialogFragment"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = ImageViewBinding.inflate(layoutInflater)
        val imageView = binding.wholeImage
        val photoFile = File(requireContext().applicationContext.filesDir, imageName)

        if (photoFile.exists()) {
            val url = photoFile.path
            Glide.with(this).load(url).centerCrop().into(imageView)
            return Dialog(requireContext()).apply {
                setContentView(binding.root)
            }
        } else {
            return super.onCreateDialog(savedInstanceState)
        }
    }
}