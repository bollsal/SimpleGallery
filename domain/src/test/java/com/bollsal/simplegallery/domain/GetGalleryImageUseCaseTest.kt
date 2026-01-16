package com.bollsal.simplegallery.domain

import com.bollsal.simplegallery.domain.entity.PageableData
import com.bollsal.simplegallery.domain.interfaces.GalleryRepository
import com.bollsal.simplegallery.domain.usecase.GetGalleryImageUseCase
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
class GetGalleryImageUseCaseTest : FunSpec() {
  init {
    test("useCase에 nextParam을 전달하지 않으면 PageableData.defaultNextParam이 설정된 결과를 전달받습니다") {
      // given
      val galleryRepository = mockk<GalleryRepository>().apply {
        coEvery { getGalleryList(PageableData.defaultNextParam) } returns PageableData(emptyList())
      }
      val useCase = GetGalleryImageUseCase(galleryRepository)

      // when
      useCase.invoke()

      // then
      coVerify(exactly = 1) {
        galleryRepository.getGalleryList(PageableData.defaultNextParam)
      }
    }

    test("useCase로 전달된 nextParam으로 설정된 결과를 전달받습니다") {
      // given
      val nextParam = PageableData.createNextParam(10, 20)
      val galleryRepository = mockk<GalleryRepository>().apply {
        coEvery { getGalleryList(nextParam) } returns PageableData(emptyList())
      }
      val useCase = GetGalleryImageUseCase(galleryRepository)

      // when
      useCase.invoke(nextParam)

      // then
      coVerify(exactly = 1) {
        galleryRepository.getGalleryList(nextParam)
      }
    }

    test("빈 데이터를 내려주더라도 useCase는 정상적으로 빈 데이터를 전달해야합니다") {
      // given
      val nextParam = mapOf("page" to 200, "limit" to 5)
      val galleryRepository = mockk<GalleryRepository>().apply {
        coEvery { getGalleryList(nextParam) } returns PageableData(emptyList(), emptyMap())
      }
      val useCase = GetGalleryImageUseCase(galleryRepository)

      // when
      val result = useCase.invoke(nextParam)

      // then
      result?.data shouldBe emptyList()
      result?.nextParam shouldBe emptyMap()
    }

    test("갤러리 데이터를 가져오지 못하면 에러가 발생합니다.") {
      // given
      val galleryRepository = mockk<GalleryRepository>().apply {
        coEvery { getGalleryList(any()) } throws Exception()
      }
      val useCase = GetGalleryImageUseCase(galleryRepository)

      // when
      val result = runCatching { useCase.invoke() }

      // then
      result.isFailure shouldBe true
    }
  }
}
