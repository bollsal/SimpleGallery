package com.bollsal.simplegallery.library.network.service

import com.bollsal.simplegallery.library.network.response.GalleryImageResp
import retrofit2.http.GET
import retrofit2.http.Query

interface GalleryService {
  @GET("v2/list")
  suspend fun getGalleyImageList(
    @Query("page") page: Int,
    @Query("limit") limit: Int
  ): List<GalleryImageResp>
}
