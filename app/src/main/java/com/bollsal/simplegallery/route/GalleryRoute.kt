package com.bollsal.simplegallery.route

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.bollsal.simplegallery.feature.main.GalleryDetailNavigation
import com.bollsal.simplegallery.feature.main.GalleryNavigation
import com.bollsal.simplegallery.feature.main.gallery.GalleryScreen
import com.bollsal.simplegallery.feature.main.gallerydetail.GalleryDetailScreen
import kotlinx.serialization.Serializable

@Serializable
sealed interface GalleryRoute {
  @Serializable
  object ScreenRoute : GalleryRoute

  @Serializable
  data class DetailRoute(val url: String) : GalleryRoute
}

fun NavGraphBuilder.galleryScreen(navigation: (GalleryNavigation) -> Unit) {
  composable<GalleryRoute.ScreenRoute> {
    GalleryScreen(navigation = navigation)
  }
}

fun NavGraphBuilder.galleryDetail(navigation: (GalleryDetailNavigation) -> Unit) {
  composable<GalleryRoute.DetailRoute> { backStackEntry ->
    val url = backStackEntry.toRoute<GalleryRoute.DetailRoute>().url
    GalleryDetailScreen(url = url, navigation = navigation)
  }
}
