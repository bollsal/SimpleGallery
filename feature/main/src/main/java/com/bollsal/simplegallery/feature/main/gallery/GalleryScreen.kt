package com.bollsal.simplegallery.feature.main.gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Incomplete
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.compose.collectAsStateWithLifecycle
import com.airbnb.mvrx.compose.mavericksViewModel
import com.bollsal.simplegallery.library.design.composable.Error
import com.bollsal.simplegallery.library.design.theme.LocalSimpleGalleryColor
import com.bollsal.simplegallery.library.design.theme.SimpleGalleryTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryScreen() {
  SimpleGalleryTheme {
    val lifecycleOwner = LocalLifecycleOwner.current
    val listState = rememberLazyGridState()

    val viewModel: GalleryViewModel = mavericksViewModel()
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
        // TODO : 어떤 형태로 토글기능 적용할지
        TopAppBar(
          title = { Text("Gallery") },
          navigationIcon = {
            IconButton(onClick = {
              viewModel.toggleColumnCount()
            }) {
            }
          }
        )
      }
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
            Gallery(
              listState = listState,
              viewModel = viewModel,
              onItemClick = { url ->
                // TODO navigation
              }
            )
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
