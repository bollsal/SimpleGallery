package com.bollsal.simplegallery.library.design

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun rememberInteractionSource() = remember { MutableInteractionSource() }

fun Modifier.shadowBy(lazyListState: LazyGridState, elevation: Dp = 8.dp): Modifier =
  this then Modifier.shadow(
    if (lazyListState.firstVisibleItemIndex == 0) {
      minOf(lazyListState.firstVisibleItemScrollOffset.toFloat().dp, elevation)
    } else {
      elevation
    }
  )
