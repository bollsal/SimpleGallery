package com.bollsal.simplegallery.domain.interfaces

import com.bollsal.simplegallery.domain.entity.GalleryImage

interface GalleryRepository {
  suspend fun getGalleryList(page: Int, limit: Int): List<GalleryImage>
}
