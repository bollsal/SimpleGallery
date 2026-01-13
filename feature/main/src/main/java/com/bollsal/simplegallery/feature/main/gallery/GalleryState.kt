package com.bollsal.simplegallery.feature.main.gallery

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.Uninitialized
import com.bollsal.simplegallery.domain.entity.GalleryImage
import com.bollsal.simplegallery.domain.entity.PageableData

data class GalleryState(
  val galleryList: List<GalleryImage> = emptyList(),
  val nextParam: Map<String, Int> = emptyMap(),
  val initialRequest: Async<PageableData<GalleryImage>?> = Uninitialized,
  val pagingRequest: Async<PageableData<GalleryImage>?> = Uninitialized,
  val columnCount: Int = 2
) : MavericksState
