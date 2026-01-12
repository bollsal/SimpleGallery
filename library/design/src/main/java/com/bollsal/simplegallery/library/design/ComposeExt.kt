package com.bollsal.simplegallery.library.design

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun rememberInteractionSource() = remember { MutableInteractionSource() }
