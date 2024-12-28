package com.example.android.tietokanta.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlin.math.roundToInt

/**
 * Decode and scale a bitmap image from a file path to fit within the specified dimensions.
 *
 * @param path The file path of the image.
 * @param destWidth The target width to scale the image to.
 * @param destHeight The target height to scale the image to.
 * @return The scaled Bitmap object.
 */
fun getScaledBitmap(path: String, destWidth: Int, destHeight: Int): Bitmap {
    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeFile(path, options)

    val srcWidth: Float = options.outWidth.toFloat()
    val srcHeight: Float = options.outHeight.toFloat()

    // Figure out how much to scale down by
    val sampleSize = if (srcHeight <= destHeight && srcWidth <= destWidth) {
        1
    } else {
        val heightScale = srcHeight / destHeight
        val widthScale = srcWidth / destWidth

        minOf(heightScale, widthScale).roundToInt()
    }

    return BitmapFactory.decodeFile(path, BitmapFactory.Options().apply {
        inSampleSize = sampleSize
    })
}