package com.bollsal.simplegallery.library.design.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Immutable
data class Colors(
  val background: Color,
  val textColor: Color,
  val rippleColor: Color,
  val progressColor: Color,
  val placeholder: Color
)

@Immutable
data class Typography(
  val titleBold: TextStyle,
  val medium: TextStyle
)

@Immutable
data class Shapes(
  val radius8: Shape,
  val radiusFull: Shape
)

val LocalSimpleGalleryColor = staticCompositionLocalOf {
  Colors(
    background = Color.Unspecified,
    textColor = Color.Unspecified,
    rippleColor = Color.Unspecified,
    progressColor = Color.Unspecified,
    placeholder = Color.Unspecified,
  )
}

val LocalTypography = staticCompositionLocalOf {
  Typography(
    titleBold = TextStyle.Default,
    medium = TextStyle.Default
  )
}

val LocalShapes = staticCompositionLocalOf {
  Shapes(
    radius8 = RoundedCornerShape(ZeroCornerSize),
    radiusFull = RoundedCornerShape(ZeroCornerSize)
  )
}

@Composable
fun SimpleGalleryTheme(content: @Composable () -> Unit) {
  val lightColor = Colors(
    background = Color(0xFFFFFFFF),
    textColor = Color(0xFF000000),
    rippleColor = Color(0x08000000),
    progressColor = Color(0xFF000000),
    placeholder = Color(0x0FFFFFFF)
  )
  val darkColor = Colors(
    background = Color(0xFF202020),
    textColor = Color(0xFFFFFFFF),
    rippleColor = Color(0x0FFFFFFF),
    progressColor = Color(0xFFFFFFFF),
    placeholder = Color(0x0FFFFFFF)
  )

  val typography = Typography(
    titleBold = TextStyle(
      fontSize = 24.sp,
      fontWeight = FontWeight.Bold,
      platformStyle = PlatformTextStyle(
        includeFontPadding = false
      )
    ),
    medium = TextStyle(
      fontSize = 15.sp,
      platformStyle = PlatformTextStyle(
        includeFontPadding = false
      )
    )
  )

  val shape = Shapes(
    radius8 = RoundedCornerShape(8.dp),
    radiusFull = CircleShape
  )

  val color = if (isSystemInDarkTheme()) darkColor else lightColor

  CompositionLocalProvider(
    LocalSimpleGalleryColor provides color,
    LocalTypography provides typography,
    LocalShapes provides shape,
    content = content
  )
}
