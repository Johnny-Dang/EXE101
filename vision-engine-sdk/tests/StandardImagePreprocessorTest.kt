package com.visionengine.tests

import com.visionengine.core.Frame
import com.visionengine.core.ImageFormat
import com.visionengine.preprocessing.StandardImagePreprocessor
import kotlin.test.Test
import kotlin.test.assertEquals

class StandardImagePreprocessorTest {
    @Test
    fun createsNormalizedTensorBuffer() {
        val preprocessor = StandardImagePreprocessor(inputWidth = 2, inputHeight = 2)

        // RGB pixels: red, green, blue, white
        val data = byteArrayOf(
            127.toByte(), 0, 0,
            0, 127.toByte(), 0,
            0, 0, 127.toByte(),
            127.toByte(), 127.toByte(), 127.toByte()
        )

        val frame = Frame(
            data = data,
            width = 2,
            height = 2,
            format = ImageFormat.RGB
        )

        val buffer = preprocessor.preprocess(frame)
        assertEquals(2 * 2 * 3 * 4, buffer.capacity())
    }
}
