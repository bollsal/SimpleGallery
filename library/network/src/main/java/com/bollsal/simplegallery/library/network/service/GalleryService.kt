package com.bollsal.simplegallery.library.network.service

import com.bollsal.simplegallery.library.network.response.GalleryImageResp
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface GalleryService {
  @GET("v2/list")
  suspend fun getGalleyImageList(
    @QueryMap nextParam: Map<String, Int>
  ): Response<List<GalleryImageResp>>
}
