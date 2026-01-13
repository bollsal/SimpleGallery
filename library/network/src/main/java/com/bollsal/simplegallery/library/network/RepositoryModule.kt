package com.bollsal.simplegallery.library.network

import com.bollsal.simplegallery.domain.interfaces.GalleryRepository
import com.bollsal.simplegallery.library.network.repository.GalleryRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

  @Binds
  fun bindGalleryRepository(repository: GalleryRepositoryImpl): GalleryRepository
}
