package com.bollsal.simplegallery.domain.entity

data class GalleryImage(
  val id: Long,
  val author: String,
  val width: Int,
  val height: Int,
  val url: String,
  val downloadUrl: String
) {
  fun calculateScaledSize(size: ImageSize): Pair<Int, Int> =
    if (size.width > width) {
      width to height
    } else {
      size.width to size.width * height / width
    }
}
