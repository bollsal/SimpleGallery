package com.bollsal.simplegallery.feature.main.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.mvrx.Async
import com.airbnb.mvrx.Loading
import com.bollsal.simplegallery.domain.entity.GalleryImage
import com.bollsal.simplegallery.domain.entity.ImageSize
import com.bollsal.simplegallery.domain.entity.PageableData
import com.bollsal.simplegallery.library.design.composable.LoadMoreIndicator
import com.bollsal.simplegallery.library.design.composable.MultipleImageItem
import com.bollsal.simplegallery.library.design.composable.SingleImageItem

@Composable
fun Gallery(
  listState: LazyGridState,
  galleryList: List<GalleryImage>,
  pagingRequest: Async<PageableData<GalleryImage>?>,
  columnCount: Int,
  onLoadMore: () -> Unit,
  onItemClick: (String) -> Unit,
  modifier: Modifier = Modifier,
) {
  val loadMore by remember(galleryList) {
    derivedStateOf {
      listState.layoutInfo.visibleItemsInfo.lastOrNull()
        ?.index
        ?.let { lastIndex ->
          lastIndex >= galleryList.lastIndex - 2
        }
    }
  }

  LaunchedEffect(loadMore) {
    if (loadMore == true) {
      onLoadMore()
    }
  }

  LazyVerticalGrid(
    modifier = modifier,
    columns = GridCells.Fixed(columnCount),
    state = listState,
    contentPadding = PaddingValues(16.dp),
    horizontalArrangement = Arrangement.spacedBy(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
  ) {
    itemsIndexed(
      items = galleryList,
      key = { index, item -> "${item.id}_$index" },
      contentType = { _, _ -> ContentType.CONTENT }
    ) { _, item ->
      val (resizedWidth, resizedHeight) = rememberResizedSize(item, ImageSize.MEDIUM_512)

      if (columnCount == 1) {
        SingleImageItem(
          modifier = Modifier.animateItem(),
          imageUrl = item.downloadUrl,
          imageWidth = resizedWidth,
          imageHeight = resizedHeight,
          name = item.author,
          onItemClick = {
            onItemClick(item.url)
          }
        )
      } else {
        MultipleImageItem(
          modifier = Modifier.animateItem(),
          imageUrl = item.downloadUrl,
          imageWidth = resizedWidth,
          imageHeight = resizedHeight,
          name = item.author,
          onItemClick = {
            onItemClick(item.url)
          }
        )
      }
    }

    if (pagingRequest is Loading) {
      item(
        key = "loadMoreIndicator",
        contentType = ContentType.LOAD_MORE,
        span = { GridItemSpan(columnCount) }
      ) {
        LoadMoreIndicator(modifier = Modifier.animateItem())
      }
    }
  }
}

@Composable
private fun rememberResizedSize(
  galleryImage: GalleryImage,
  imageSize: ImageSize
): Pair<Int, Int> = remember(galleryImage.width, galleryImage.height, imageSize) {
  galleryImage.calculateScaledSize(imageSize)
}

enum class ContentType {
  CONTENT,
  LOAD_MORE
}
