package com.visionengine.tests

import com.visionengine.core.Frame
import com.visionengine.core.ImageFormat
import com.visionengine.postprocessing.LabelMapper
import com.visionengine.postprocessing.YoloPostprocessor
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class YoloPostprocessorTest {
    @Test
    fun decodeAppliesThresholdAndNms() {
        val postprocessor = YoloPostprocessor(
            confidenceThreshold = 0.5f,
            iouThreshold = 0.45f,
            labelMapper = LabelMapper(listOf("person", "carrot", "car"))
        )

        val frame = Frame(
            data = ByteArray(640 * 640 * 3),
            width = 640,
            height = 640,
            format = ImageFormat.RGB
        )

        val output = floatArrayOf(
            0.10f, 0.10f, 0.40f, 0.60f, 0.91f, 1f,
            0.12f, 0.12f, 0.39f, 0.59f, 0.87f, 1f,
            0.50f, 0.20f, 0.80f, 0.70f, 0.40f, 2f
        )

        val detections = postprocessor.postprocess(output, frame)

        assertEquals(1, detections.size)
        assertEquals("carrot", detections.first().label)
        assertTrue(detections.first().confidence >= 0.5f)
    }
}
