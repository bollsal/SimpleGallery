package com.bollsal.simplegallery.library.network.mapper

import com.bollsal.simplegallery.domain.interfaces.Mapper
import javax.inject.Inject
import okhttp3.Headers

class NextParamMapper @Inject constructor() : Mapper<Headers, Map<String, Int>> {
  /**
   * header link format
   * <https://picsum.photos/v2/list?page=2&limit=20>; rel="prev", <https://picsum.photos/v2/list?page=4&limit=20>; rel="next"
   */
  val regex = """<[^?]*\?([^>]*)>;\s*rel="([^"]+)"""".toRegex()
  override fun map(from: Headers): Map<String, Int> = from["link"]?.parseLink() ?: emptyMap()

  private fun String.parseLink(): Map<String, Int>? =
    runCatching {
      regex.findAll(this)
        .find { it.groupValues[2] == "next" }
        ?.let { result ->
          result.groupValues[1]
            .split("&")
            .associate {
              it.split("=").let {
                it[0] to it[1].toInt()
              }
            }
        }
    }
      .getOrNull()
}
