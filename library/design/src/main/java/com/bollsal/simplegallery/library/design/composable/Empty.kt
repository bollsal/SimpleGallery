package com.bollsal.simplegallery.library.design.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bollsal.simplegallery.library.design.R
import com.bollsal.simplegallery.library.design.theme.LocalSimpleGalleryColor
import com.bollsal.simplegallery.library.design.theme.LocalTypography

@Composable
fun Empty(
  modifier: Modifier = Modifier,
  message: String = stringResource(R.string.empty_contents)
) {
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      modifier = Modifier.padding(bottom = 8.dp),
      text = message,
      style = LocalTypography.current.titleBold,
      color = LocalSimpleGalleryColor.current.textColor
    )
  }
}
