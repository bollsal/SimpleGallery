package com.bollsal.simplegallery.library.network

import com.bollsal.simplegallery.library.network.mapper.NextParamMapper
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import okhttp3.Headers

class NextParamMapperTest : FunSpec() {
  private val nextParamMapper = NextParamMapper()

  init {
    test("rel값이 next라면 link의 query를 key-value 형태로 리턴합니다") {
      // given
      val link = """
        <https://picsum.photos/v2/list?page=4&limit=20>; rel="next"
      """.trimIndent()
      val headers = Headers.Builder()
        .add("link", link)
        .build()

      // when
      val result = nextParamMapper.map(headers)

      // then
      result shouldBe mapOf("page" to 4, "limit" to 20)
    }

    test("rel값에 next가 없다면 빈 map을 리턴합니다") {
      // given
      val link = """
        <https://picsum.photos/v2/list?page=4&limit=20>; rel="prev"
      """.trimIndent()
      val headers = Headers.Builder()
        .add("link", link)
        .build()

      // when
      val result = nextParamMapper.map(headers)

      // then
      result shouldBe emptyMap()
    }

    test("rel값이 next인 경우에만 map에 추가됩니다") {
      // given
      val link = """
        <https://picsum.photos/v2/list?page=2&limit=20>; rel="prev", <https://picsum.photos/v2/list?page=4&limit=20>; rel="next"
      """.trimIndent()
      val headers = Headers.Builder()
        .add("link", link)
        .build()

      // when
      val result = nextParamMapper.map(headers)

      // then
      result shouldBe mapOf("page" to 4, "limit" to 20)
    }
  }
}
