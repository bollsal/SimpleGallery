package com.bollsal.simplegallery.library.design

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bollsal.simplegallery.library.design.theme.LocalShapes
import com.bollsal.simplegallery.library.design.theme.LocalSimpleGalleryColor
import com.bollsal.simplegallery.library.design.theme.LocalTypography
import com.bollsal.simplegallery.library.design.theme.SimpleGalleryTheme

@Composable
fun SingleImageItem(
  imageUrl: String,
  name: String,
  onItemClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .height(72.dp)
      .background(LocalSimpleGalleryColor.current.background)
      .clickable(
        interactionSource = rememberInteractionSource(),
        indication = ripple(color = LocalSimpleGalleryColor.current.rippleColor),
        onClick = onItemClick
      ),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Box(
      modifier = Modifier
        .fillMaxHeight()
        .aspectRatio(16 / 9f)
        .background(Color.Red)
    ) {
      // TODO : 이후 이미지 교체 필요
      Text(text = "이미지")
    }

    Text(
      modifier = Modifier.padding(start = 8.dp),
      text = name,
      color = LocalSimpleGalleryColor.current.textColor,
      style = LocalTypography.current.medium
    )
  }
}

@Composable
fun MultipleImageItem(
  imageUrl: String,
  name: String,
  onItemClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .clip(LocalShapes.current.radius8)
      .background(LocalSimpleGalleryColor.current.background)
      .clickable(
        interactionSource = rememberInteractionSource(),
        indication = ripple(color = LocalSimpleGalleryColor.current.rippleColor),
        onClick = onItemClick
      )
  ) {
    Box(
      modifier = Modifier
        .fillMaxHeight()
        .aspectRatio(1f)
        .background(Color.Red)
    ) {
      // TODO : 이후 이미지 교체 필요
      Text(text = "이미지")
    }

    Text(
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp),
      text = name,
      color = LocalSimpleGalleryColor.current.textColor,
      style = LocalTypography.current.medium
    )
  }
}

@Preview
@Composable
private fun PreviewSingleImageItem() {
  SimpleGalleryTheme {
    Column {
      SingleImageItem(
        imageUrl = "https://picsum.photos/id/0/5000/3333",
        name = "Alejandro Escamilla",
        onItemClick = {}
      )
    }
  }
}

@Preview
@Composable
private fun PreviewMultipleImageItem() {
  SimpleGalleryTheme {
    LazyVerticalGrid(
      state = rememberLazyGridState(),
      columns = GridCells.Fixed(2),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      item {
        MultipleImageItem(
          imageUrl = "https://picsum.photos/id/0/5000/3333",
          name = "Alejandro Escamilla",
          onItemClick = {}
        )
      }
      item {
        MultipleImageItem(
          imageUrl = "https://picsum.photos/id/1/5000/3333",
          name = "Paul Jarvis",
          onItemClick = {}
        )
      }
    }
  }
}
