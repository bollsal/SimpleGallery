package com.bollsal.simplegallery.domain.usecase

import com.bollsal.simplegallery.domain.entity.GalleryImage
import com.bollsal.simplegallery.domain.interfaces.GalleryRepository
import javax.inject.Inject

class GetGalleryImageUseCase @Inject constructor(private val repository: GalleryRepository) {
  suspend operator fun invoke(
    page: Int,
    limit: Int
  ): List<GalleryImage> =
    repository.getGalleryList(
      page = page,
      limit = limit
    )
}
