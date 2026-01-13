package com.bollsal.simplegallery.library.network.response

import com.bollsal.simplegallery.domain.entity.GalleryImage
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GalleryImageResp(
  @field:Json(name = "id")
  val id: Long,
  @field:Json(name = "author")
  val author: String,
  @field:Json(name = "width")
  val width: Int,
  @field:Json(name = "height")
  val height: Int,
  @field:Json(name = "url")
  val url: String,
  @field:Json(name = "download_url")
  val downloadUrl: String
) {
  fun asGalleryImage() = GalleryImage(
    id = id,
    author = author,
    width = width,
    height = height,
    url = url,
    downloadUrl = downloadUrl
  )
}
