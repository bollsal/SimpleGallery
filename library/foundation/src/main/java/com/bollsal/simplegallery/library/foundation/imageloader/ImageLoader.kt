package com.bollsal.simplegallery.library.foundation.imageloader

import android.content.Context
import android.graphics.Bitmap
import androidx.collection.LruCache
import com.bollsal.simplegallery.library.foundation.decodeFileToBitmap
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import timber.log.Timber

@Singleton
class ImageLoader @Inject constructor(@ApplicationContext private val context: Context) {
  private val memoryCache: LruCache<String, Bitmap>
  private val diskCache: SimpleDiskLruCache

  init {
    val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
    val cacheSize = maxMemory / 8

    memoryCache = object : LruCache<String, Bitmap>(cacheSize) {
      override fun sizeOf(key: String, value: Bitmap): Int = value.byteCount / 1024
    }

    diskCache = SimpleDiskLruCache(
      cacheDir = File(context.cacheDir, "image_cache"),
      cacheLimitSize = 1024 * 1024 * 50
    )
  }

  suspend fun load(
    imageUrl: String,
    width: Int,
    height: Int
  ): Bitmap? = withContext(Dispatchers.IO) {
    memoryCache.get(imageUrl)?.let { memoryCacheBitmap ->
      return@withContext memoryCacheBitmap
    }

    diskCache.get(imageUrl)?.let { diskCacheFile ->
      ensureActive()
      return@withContext decodeFileToBitmapWithCache(
        imageUrl = imageUrl,
        file = diskCacheFile,
        width = width,
        height = height
      )
    }

    downLoadImageWithCache(
      imageUrl = imageUrl,
      width = width,
      height = height
    )
  }

  private suspend fun downLoadImageWithCache(
    imageUrl: String,
    width: Int,
    height: Int
  ): Bitmap? = withContext(Dispatchers.IO) {
    ensureActive()

    runCatching {
      val connection = URL(imageUrl).openConnection() as HttpURLConnection
      connection.connectTimeout = 10 * 1000
      connection.getInputStream().use { inputStream ->
        if (isActive) {
          diskCache.put(key = imageUrl, value = inputStream)
        }
      }
    }.onFailure {
      Timber.e("SocketTimeoutException")
    }

    diskCache.get(imageUrl)?.let { file ->
      ensureActive()
      decodeFileToBitmapWithCache(
        imageUrl = imageUrl,
        file = file,
        width = width,
        height = height
      )
    }
  }

  private fun decodeFileToBitmapWithCache(
    imageUrl: String,
    file: File,
    width: Int,
    height: Int
  ): Bitmap {
    val bitmap = decodeFileToBitmap(file, width, height)
    memoryCache.put(imageUrl, bitmap)
    return bitmap
  }
}
