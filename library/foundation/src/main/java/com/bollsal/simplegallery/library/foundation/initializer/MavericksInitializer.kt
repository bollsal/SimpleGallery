package com.bollsal.simplegallery.library.foundation.initializer

import android.content.Context
import android.os.StrictMode
import androidx.startup.Initializer
import com.airbnb.mvrx.Mavericks
import com.airbnb.mvrx.MavericksViewModelConfigFactory
import com.airbnb.mvrx.asContextElement
import com.airbnb.mvrx.navigation.DefaultNavigationViewModelDelegateFactory
import com.bollsal.simplegallery.library.foundation.BuildConfig
import kotlin.coroutines.EmptyCoroutineContext

class MavericksInitializer : Initializer<Unit> {
  override fun create(context: Context) {
    Mavericks.initialize(
      context,
      viewModelDelegateFactory = DefaultNavigationViewModelDelegateFactory()
    )
    Mavericks.viewModelConfigFactory = MavericksViewModelConfigFactory(
      debugMode = BuildConfig.DEBUG,
      storeContextOverride = if (BuildConfig.DEBUG) {
        StrictMode.ThreadPolicy
          .Builder()
          .detectNetwork()
          .penaltyDialog()
          .build()
          .asContextElement()
      } else {
        EmptyCoroutineContext
      }
    )
  }

  override fun dependencies(): List<Class<out Initializer<*>?>?> = mutableListOf()
}
