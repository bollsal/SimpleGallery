package com.bollsal.simplegallery.library.foundation

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File

/**
 * https://developer.android.com/topic/performance/graphics/load-bitmap#load-bitmap
 */
fun BitmapFactory.Options.calculateSampleSize(reqWidth: Int, reqHeight: Int): Int {
  var inSampleSize = 1

  if (outHeight > reqHeight || outWidth > reqWidth) {
    val halfHeight = outHeight / 2
    val halfWidth = outWidth / 2

    // Calculate the largest inSampleSize value that is a power of 2 and keeps both
    // height and width larger than the requested height and width.
    while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
      inSampleSize *= 2
    }
  }

  return inSampleSize
}

fun decodeFileToBitmap(file: File, maxWidth: Int, maxHeight: Int): Bitmap =
  BitmapFactory.Options()
    .apply {
      inJustDecodeBounds = true
      BitmapFactory.decodeFile(file.toString(), this)
      inSampleSize = calculateSampleSize(maxWidth, maxHeight)
      inJustDecodeBounds = false
    }
    .let {
      BitmapFactory.decodeFile(file.toString(), it)
    }
