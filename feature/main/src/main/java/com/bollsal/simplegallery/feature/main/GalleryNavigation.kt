package com.bollsal.simplegallery.feature.main

sealed class GalleryNavigation {
  data class NavigateToDetail(val url: String) : GalleryNavigation()
}

sealed class GalleryDetailNavigation {
  object Back : GalleryDetailNavigation()
}
