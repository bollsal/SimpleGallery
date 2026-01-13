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
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.compose.collectAsStateWithLifecycle
import com.bollsal.simplegallery.library.design.composable.LoadMoreIndicator
import com.bollsal.simplegallery.library.design.composable.MultipleImageItem
import com.bollsal.simplegallery.library.design.composable.SingleImageItem

@Composable
fun Gallery(
  listState: LazyGridState,
  viewModel: GalleryViewModel,
  onItemClick: (String) -> Unit,
  modifier: Modifier = Modifier,
) {
  val galleryList by viewModel.collectAsStateWithLifecycle(GalleryState::galleryList)
  val pagingRequest by viewModel.collectAsStateWithLifecycle(GalleryState::pagingRequest)
  val columnCount by viewModel.collectAsStateWithLifecycle(GalleryState::columnCount)

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
      viewModel.fetchLoadMore()
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
    ) { index, item ->
      if (columnCount == 1) {
        SingleImageItem(
          modifier = Modifier.animateItem(),
          // FIXME
          // imageUrl = item.downloadUrl,
          imageUrl = "$index",
          name = item.author,
          onItemClick = {
            onItemClick(item.url)
          }
        )
      } else {
        MultipleImageItem(
          modifier = Modifier.animateItem(),
          // FIXME
          // imageUrl = item.downloadUrl,
          imageUrl = "$index",
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

enum class ContentType {
  CONTENT,
  LOAD_MORE
}
