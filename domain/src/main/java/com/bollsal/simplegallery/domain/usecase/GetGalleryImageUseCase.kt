package com.bollsal.simplegallery.domain.usecase

import com.bollsal.simplegallery.domain.entity.GalleryImage
import com.bollsal.simplegallery.domain.entity.PageableData
import com.bollsal.simplegallery.domain.interfaces.GalleryRepository
import javax.inject.Inject

class GetGalleryImageUseCase @Inject constructor(private val repository: GalleryRepository) {
  suspend operator fun invoke(nextParam: Map<String, Int> = PageableData.defaultNextParam): PageableData<GalleryImage>? =
    repository.getGalleryList(nextParam)
}
