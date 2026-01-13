package com.bollsal.simplegallery.library.network

import com.bollsal.simplegallery.library.network.service.GalleryService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
  private const val BASE_URL = "https://picsum.photos/"

  @Provides
  @Singleton
  fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
      .baseUrl(BASE_URL)
      .client(okHttpClient)
      .addConverterFactory(MoshiConverterFactory.create())
      .build()

  @Provides
  @Singleton
  fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().apply {
    connectTimeout(5, TimeUnit.SECONDS)
    readTimeout(5, TimeUnit.SECONDS)
    writeTimeout(5, TimeUnit.SECONDS)

    if (BuildConfig.DEBUG) {
      addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
    }
  }
    .build()

  @Provides
  fun provideGalleryService(retrofit: Retrofit): GalleryService = retrofit.create()
}
