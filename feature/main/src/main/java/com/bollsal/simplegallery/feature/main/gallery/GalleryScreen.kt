package com.bollsal.simplegallery.feature.main.gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Incomplete
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.compose.collectAsStateWithLifecycle
import com.airbnb.mvrx.compose.mavericksViewModel
import com.bollsal.simplegallery.feature.main.GalleryNavigation
import com.bollsal.simplegallery.library.design.R
import com.bollsal.simplegallery.library.design.composable.Empty
import com.bollsal.simplegallery.library.design.composable.Error
import com.bollsal.simplegallery.library.design.shadowBy
import com.bollsal.simplegallery.library.design.theme.LocalSimpleGalleryColor
import com.bollsal.simplegallery.library.design.theme.SimpleGalleryTheme
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryScreen(navigation: (GalleryNavigation) -> Unit) {
  SimpleGalleryTheme {
    val coroutineScope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current
    val listState = rememberLazyGridState()

    val viewModel: GalleryViewModel = mavericksViewModel()
    val galleryList by viewModel.collectAsStateWithLifecycle(GalleryState::galleryList)
    val pagingRequest by viewModel.collectAsStateWithLifecycle(GalleryState::pagingRequest)
    val columnCount by viewModel.collectAsStateWithLifecycle(GalleryState::columnCount)
    val initialRequest by viewModel.collectAsStateWithLifecycle(GalleryState::initialRequest)

    DisposableEffect(lifecycleOwner) {
      val lifeCycleObserver = object : DefaultLifecycleObserver {
        override fun onStart(owner: LifecycleOwner) {
          viewModel.fetch()
        }
      }
      lifecycleOwner.lifecycle.addObserver(lifeCycleObserver)

      onDispose {
        lifecycleOwner.lifecycle.removeObserver(lifeCycleObserver)
      }
    }

    Scaffold(
      modifier = Modifier
        .fillMaxSize()
        .background(LocalSimpleGalleryColor.current.background)
        .systemBarsPadding(),
      topBar = {
        TopAppBar(
          modifier = Modifier.shadowBy(listState),
          title = {
            Text(
              modifier = Modifier
                .clickable(
                  onClick = { coroutineScope.launch { listState.animateScrollToItem(0) } }
                ),
              text = "Gallery",
              color = LocalSimpleGalleryColor.current.textColor
            )
          },
          colors = TopAppBarDefaults.topAppBarColors(containerColor = LocalSimpleGalleryColor.current.background),
          actions = {
            IconButton(onClick = viewModel::toggleColumnCount) {
              Icon(
                painterResource(R.drawable.toggle),
                tint = LocalSimpleGalleryColor.current.textColor,
                contentDescription = null,
              )
            }
          }
        )
      },
    ) { innerPadding ->
      Box(
        modifier = Modifier
          .fillMaxSize()
          .background(LocalSimpleGalleryColor.current.background)
          .padding(innerPadding)
      ) {
        when (initialRequest) {
          is Incomplete -> {
            CircularProgressIndicator(
              modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .wrapContentSize(),
              color = LocalSimpleGalleryColor.current.progressColor
            )
          }

          is Success -> {
            if (galleryList.isEmpty()) {
              Empty(modifier = Modifier.fillMaxSize())
            } else {
              Gallery(
                listState = listState,
                galleryList = galleryList.toPersistentList(),
                pagingRequest = pagingRequest,
                columnCount = columnCount,
                onLoadMore = {
                  viewModel.fetchLoadMore()
                },
                onItemClick = { url ->
                  navigation(GalleryNavigation.NavigateToDetail(url))
                }
              )
            }
          }

          is Fail -> {
            Error(modifier = Modifier.fillMaxSize()) {
              viewModel.fetch()
            }
          }

          else -> Unit
        }
      }
    }
  }
}
