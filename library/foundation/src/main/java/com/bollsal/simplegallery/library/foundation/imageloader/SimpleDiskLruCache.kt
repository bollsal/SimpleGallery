package com.bollsal.simplegallery.library.foundation.imageloader

import java.io.File
import java.io.InputStream

class SimpleDiskLruCache(
  private val cacheDir: File,
  private val cacheLimitSize: Long
) {
  private val cacheMap: LinkedHashMap<String, File> = linkedMapOf()
  private var currentCacheSize: Long = 0

  init {
    if (!cacheDir.exists()) {
      cacheDir.mkdirs()
    } else {
      cacheDir.listFiles()?.forEach { it.delete() }
    }
  }

  fun put(key: String, value: InputStream) {
    val file = File(cacheDir, key.hashCode().toString())
    file.outputStream().use { outputStream ->
      value.copyTo(outputStream)
    }
    synchronized(cacheMap) {
      currentCacheSize += file.length()
      cacheMap[key] = file
      trimToSize()
    }
  }

  fun get(key: String): File? {
    synchronized(cacheMap) {
      val file = cacheMap[key] ?: return null
      cacheMap.remove(key)
      cacheMap[key] = file
      return file
    }
  }

  private fun trimToSize() {
    while (currentCacheSize > cacheLimitSize && cacheMap.isNotEmpty()) {
      val (key, file) = cacheMap.entries.iterator().next()
      if (file.exists()) {
        currentCacheSize -= file.length()
        file.delete()
      }
      cacheMap.remove(key)
    }
  }
}
