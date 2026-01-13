package com.bollsal.simplegallery.library.network.repository

import com.bollsal.simplegallery.domain.entity.GalleryImage
import com.bollsal.simplegallery.domain.entity.PageableData
import com.bollsal.simplegallery.domain.interfaces.GalleryRepository
import com.bollsal.simplegallery.library.network.mapper.NextParamMapper
import com.bollsal.simplegallery.library.network.service.GalleryService
import javax.inject.Inject

class GalleryRepositoryImpl @Inject constructor(
  private val galleryService: GalleryService,
  private val nextParamMapper: NextParamMapper
) : GalleryRepository {

  override suspend fun getGalleryList(nextParam: Map<String, Int>): PageableData<GalleryImage>? {
    val response = galleryService.getGalleyImageList(nextParam)
    return response.body()?.let { body ->
      PageableData(
        data = body.map { it.asGalleryImage() },
        nextParam = nextParamMapper.map(response.headers())
      )
    }
  }
}
