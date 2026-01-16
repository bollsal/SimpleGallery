package com.bollsal.simplegallery.feature.main.gallerydetail

import android.util.Xml
import android.view.View.LAYER_TYPE_HARDWARE
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.viewinterop.AndroidView
import com.bollsal.simplegallery.feature.main.GalleryDetailNavigation
import com.bollsal.simplegallery.library.design.R
import com.bollsal.simplegallery.library.design.theme.LocalSimpleGalleryColor
import com.bollsal.simplegallery.library.design.theme.SimpleGalleryTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryDetailScreen(
  url: String,
  navigation: (GalleryDetailNavigation) -> Unit
) {
  var webView by remember { mutableStateOf<WebView?>(null) }
  var canGoBack by remember { mutableStateOf(false) }
  var canGoForward by remember { mutableStateOf(false) }
  var isLoading by remember(url) { mutableStateOf(true) }

  SimpleGalleryTheme {
    DisposableEffect(Unit) {
      onDispose {
        webView?.destroy()
      }
    }

    Scaffold(
      modifier = Modifier
        .fillMaxSize()
        .background(LocalSimpleGalleryColor.current.background)
        .systemBarsPadding(),
      topBar = {
        TopAppBar(
          title = {
            Text(
              text = "Gallery Detail",
              color = LocalSimpleGalleryColor.current.textColor
            )
          },
          colors = TopAppBarDefaults.topAppBarColors(containerColor = LocalSimpleGalleryColor.current.background),
          navigationIcon = {
            IconButton(onClick = { navigation(GalleryDetailNavigation.Back) }) {
              Icon(
                painterResource(R.drawable.back),
                tint = LocalSimpleGalleryColor.current.textColor,
                contentDescription = null,
              )
            }
          },
          actions = {
            WebViewNavigationIcon(
              iconPainter = painterResource(R.drawable.back),
              enabled = canGoBack
            ) {
              webView?.goBack()
            }
            WebViewNavigationIcon(
              iconPainter = painterResource(R.drawable.forward),
              enabled = canGoForward
            ) {
              webView?.goForward()
            }
          }
        )
      },
    ) { innerPadding ->
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(innerPadding)
      ) {
        AndroidView(
          modifier = Modifier.fillMaxSize(),
          factory = { context ->
            WebView(context).apply {
              setLayerType(LAYER_TYPE_HARDWARE, null)
              webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                  isLoading = false
                }

                override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
                  canGoBack = view?.canGoBack() ?: false
                  canGoForward = view?.canGoForward() ?: false
                }
              }
              layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
              )

              settings.apply {
                javaScriptEnabled = true
                useWideViewPort = true
                loadWithOverviewMode = true
                defaultTextEncodingName = Xml.Encoding.UTF_8.name
              }
            }
              .also {
                webView = it
              }
          },
          update = { view ->
            if (view.url != url) {
              view.loadUrl(url)
            }
          }
        )

        if (isLoading) {
          CircularProgressIndicator(
            modifier = Modifier
              .padding(innerPadding)
              .fillMaxSize()
              .wrapContentSize(),
            color = LocalSimpleGalleryColor.current.progressColor
          )
        }
      }
    }
  }
}

@Composable
private fun WebViewNavigationIcon(
  iconPainter: Painter,
  enabled: Boolean,
  onClick: () -> Unit
) {
  IconButton(
    onClick = onClick,
    enabled = enabled
  ) {
    Icon(
      painter = iconPainter,
      tint = when (enabled) {
        true -> LocalSimpleGalleryColor.current.textColor
        else -> LocalSimpleGalleryColor.current.disabled
      },
      contentDescription = null,
    )
  }
}
