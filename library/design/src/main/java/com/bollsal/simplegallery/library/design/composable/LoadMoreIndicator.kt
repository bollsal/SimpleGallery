package com.bollsal.simplegallery.library.design.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bollsal.simplegallery.library.design.theme.LocalSimpleGalleryColor
import com.bollsal.simplegallery.library.design.theme.SimpleGalleryTheme

@Composable
fun LoadMoreIndicator(
  modifier: Modifier = Modifier,
  size: Dp = 24.dp
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .background(LocalSimpleGalleryColor.current.background)
      .padding(14.dp),
    contentAlignment = Alignment.Center
  ) {
    CircularProgressIndicator(
      modifier = Modifier.size(size),
      color = LocalSimpleGalleryColor.current.progressColor
    )
  }
}

@Preview
@Composable
fun PreviewLoadMoreIndicator() {
  SimpleGalleryTheme {
    LoadMoreIndicator()
  }
}
