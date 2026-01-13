package com.bollsal.simplegallery.feature.main.gallery

import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.bollsal.simplegallery.domain.usecase.GetGalleryImageUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class GalleryViewModel @AssistedInject constructor(
  @Assisted galleryState: GalleryState,
  private val getGalleryImageList: GetGalleryImageUseCase
) : MavericksViewModel<GalleryState>(galleryState) {

  fun fetch() = withState { state ->
    if (!state.initialRequest.shouldLoad) return@withState

    suspend { getGalleryImageList() }
      .execute { result ->
        copy(
          initialRequest = result,
          galleryList = result()?.data ?: emptyList(),
          nextParam = result()?.nextParam ?: emptyMap()
        )
      }
  }

  fun fetchLoadMore() = withState { state ->
    if (state.pagingRequest is Loading) return@withState
    if (state.nextParam.isEmpty()) return@withState

    suspend { getGalleryImageList(nextParam = state.nextParam) }
      .execute { result ->
        when (result) {
          is Success -> {
            copy(
              pagingRequest = result,
              galleryList = galleryList + (result()?.data ?: emptyList()),
              nextParam = result()?.nextParam ?: emptyMap()
            )
          }

          else -> {
            copy(pagingRequest = result)
          }
        }
      }
  }

  fun toggleColumnCount() = setState {
    copy(columnCount = if (columnCount == 1) 2 else 1)
  }

  @AssistedFactory
  interface Factory : AssistedViewModelFactory<GalleryViewModel, GalleryState> {
    override fun create(state: GalleryState): GalleryViewModel
  }

  companion object : MavericksViewModelFactory<GalleryViewModel, GalleryState> by hiltMavericksViewModelFactory()
}
