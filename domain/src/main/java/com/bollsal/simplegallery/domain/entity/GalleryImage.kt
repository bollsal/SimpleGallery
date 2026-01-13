package com.bollsal.simplegallery.domain.entity

data class GalleryImage(
  val id: Long,
  val author: String,
  val width: Int,
  val height: Int,
  val url: String,
  val downloadUrl: String
)
