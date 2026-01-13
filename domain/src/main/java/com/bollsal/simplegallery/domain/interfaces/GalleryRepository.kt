package com.bollsal.simplegallery.domain.interfaces

import com.bollsal.simplegallery.domain.entity.GalleryImage
import com.bollsal.simplegallery.domain.entity.PageableData

interface GalleryRepository {
  suspend fun getGalleryList(nextParam: Map<String, Int>): PageableData<GalleryImage>?
}
