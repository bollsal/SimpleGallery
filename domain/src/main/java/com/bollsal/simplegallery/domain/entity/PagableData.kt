package com.bollsal.simplegallery.domain.entity

data class PageableData<T>(
  val data: List<T>,
  val nextParam: Map<String, Int>? = null
) {
  companion object {
    private const val KEY_PAGE = "page"
    private const val KEY_LIMIT = "limit"

    val defaultNextParam: Map<String, Int> = mapOf(
      KEY_PAGE to 3,
      KEY_LIMIT to 20
    )

    fun createNextParam(page: Int, limit: Int) =
      mapOf(
        KEY_PAGE to page,
        KEY_LIMIT to limit
      )
  }
}
