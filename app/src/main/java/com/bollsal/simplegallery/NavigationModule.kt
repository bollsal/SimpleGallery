package com.bollsal.simplegallery

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.bollsal.simplegallery.feature.main.GalleryDetailNavigation
import com.bollsal.simplegallery.feature.main.GalleryNavigation
import com.bollsal.simplegallery.route.GalleryRoute
import com.bollsal.simplegallery.route.galleryDetail
import com.bollsal.simplegallery.route.galleryScreen

@Composable
fun NavigationModule(navController: NavHostController) {
  NavHost(navController = navController, startDestination = GalleryRoute.ScreenRoute) {
    galleryScreen { galleryNavigation ->
      when (galleryNavigation) {
        is GalleryNavigation.NavigateToDetail -> {
          navController.navigate(GalleryRoute.DetailRoute(galleryNavigation.url))
        }
      }
    }
    galleryDetail { galleryDetailNavigation ->
      when (galleryDetailNavigation) {
        is GalleryDetailNavigation.Back -> {
          navController.navigateUp()
        }
      }
    }
  }
}
