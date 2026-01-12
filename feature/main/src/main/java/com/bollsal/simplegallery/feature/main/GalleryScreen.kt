package com.bollsal.simplegallery.feature.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bollsal.simplegallery.library.design.theme.SimpleGalleryTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryScreen() {
  SimpleGalleryTheme {
    val listState = rememberLazyGridState()

    Scaffold(
      modifier = Modifier
        .fillMaxSize()
        .systemBarsPadding(),
      topBar = {
        TopAppBar(
          title = { Text("Gallery") }
        )
      }
    ) { innerPadding ->

      Gallery(
        modifier = Modifier
          .systemBarsPadding()
          .padding(innerPadding),
        listState = listState
      )
    }
  }
}

@Composable
private fun Gallery(
  listState: LazyGridState,
  modifier: Modifier = Modifier
) {
  LazyVerticalGrid(
    modifier = modifier,
    columns = GridCells.Fixed(2),
    state = listState,
    horizontalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    // TODO
  }
}
