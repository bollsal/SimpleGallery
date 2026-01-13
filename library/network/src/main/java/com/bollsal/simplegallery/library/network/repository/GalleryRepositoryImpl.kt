package com.bollsal.simplegallery.library.network.repository

import com.bollsal.simplegallery.domain.entity.GalleryImage
import com.bollsal.simplegallery.domain.interfaces.GalleryRepository
import com.bollsal.simplegallery.library.network.service.GalleryService
import javax.inject.Inject

class GalleryRepositoryImpl @Inject constructor(private val galleryService: GalleryService) : GalleryRepository {
  override suspend fun getGalleryList(page: Int, limit: Int): List<GalleryImage> =
    galleryService.getGalleyImageList(
      page = page,
      limit = limit
    )
      .map { it.asGalleryImage() }
}
