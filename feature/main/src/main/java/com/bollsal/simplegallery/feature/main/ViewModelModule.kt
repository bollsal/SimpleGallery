package com.bollsal.simplegallery.feature.main

import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.MavericksViewModelComponent
import com.airbnb.mvrx.hilt.ViewModelKey
import com.bollsal.simplegallery.feature.main.gallery.GalleryViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.multibindings.IntoMap

@Module
@InstallIn(MavericksViewModelComponent::class)
interface ViewModelModule {

  @Binds
  @IntoMap
  @ViewModelKey(GalleryViewModel::class)
  fun galleryViewModel(factory: GalleryViewModel.Factory): AssistedViewModelFactory<*, *>
}
