package com.example.se121p11new.presentation.image_captioning_screen

import android.graphics.Bitmap
import com.example.se121p11new.data.MockImageRepostiory
import com.example.se121p11new.domain.repository.ImageRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class ImageCaptioningViewModelTest {

    private val mockImageRepository: ImageRepository = MockImageRepostiory()
    private val viewModel = ImageCaptioningViewModel(mockImageRepository)
    private val bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)

    @BeforeEach
    fun setUp() {
//        mockImageRepository.stub {
//            on {
//                runTest { launch { generateEnglishText(bitmap) } }
//            } doReturn flow {
//                emit(Resource.Success("Hello"))
//            }
//
//            on {
//                runBlocking { generateVietnameseText("Hello") }
//            } doReturn flow {
//                emit(Resource.Success("Xin chào"))
//            }
//        }

    }

    @AfterEach
    fun tearDown() {
    }

    @DisplayName("WHEN pass a bitmap THEN return a image captioning string")
    @Test
    fun generateText() {
        viewModel.generateText(bitmap)

        assertEquals("Xin chào", viewModel.generatedVietnameseText.value.data)

    }
}