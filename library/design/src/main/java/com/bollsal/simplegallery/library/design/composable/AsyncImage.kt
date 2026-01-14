package com.bollsal.simplegallery.library.design.composable

import android.graphics.Bitmap
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bollsal.simplegallery.library.design.R
import com.bollsal.simplegallery.library.design.theme.LocalSimpleGalleryColor
import com.bollsal.simplegallery.library.foundation.imageloader.ImageLoaderEntryPoint
import dagger.hilt.android.EntryPointAccessors

@Composable
fun AsyncImage(
  imageUrl: String,
  modifier: Modifier = Modifier,
  placeholder: Color = LocalSimpleGalleryColor.current.placeholder,
  crossFade: Boolean = true
) {
  val context = LocalContext.current
  val imageLoader = remember {
    EntryPointAccessors.fromApplication<ImageLoaderEntryPoint>(context).imageLoader()
  }

  var isLoading by remember(imageUrl) { mutableStateOf(true) }
  var isError by remember(imageUrl) { mutableStateOf(false) }
  var bitmap by remember(imageUrl) { mutableStateOf<Bitmap?>(null) }
  val animatedAlpha by animateFloatAsState(
    targetValue = when (crossFade) {
      true -> if (bitmap == null) 0f else 1f
      false -> 1f
    },
    animationSpec = tween(durationMillis = 300)
  )

  LaunchedEffect(imageUrl) {
    bitmap = imageLoader.load(imageUrl)

    isLoading = false
    isError = bitmap == null
  }

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(color = placeholder),
    contentAlignment = Alignment.Center
  ) {
    when {
      isLoading -> {
        CircularProgressIndicator(
          modifier = Modifier.size(24.dp),
          color = LocalSimpleGalleryColor.current.progressColor
        )
      }

      isError -> {
        Icon(
          painterResource(R.drawable.error),
          tint = Color.White,
          contentDescription = null
        )
      }

      bitmap != null -> {
        Image(
          modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
              alpha = animatedAlpha
            },
          bitmap = bitmap!!.asImageBitmap(),
          contentScale = ContentScale.Crop,
          contentDescription = null
        )
      }
    }
  }
}
