package com.visionengine.tests

import com.visionengine.api.EngineConfig
import com.visionengine.api.VisionEngineFactory
import com.visionengine.core.Frame
import com.visionengine.core.Result
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MockVisionEngineTest {
    @Test
    fun mockEngineReturnsCarrotDetection() = runBlocking {
        val engine = VisionEngineFactory.createMock()
        engine.init(EngineConfig(modelPath = "models/mock.tflite"))

        val frame = Frame(
            data = ByteArray(640 * 640),
            width = 640,
            height = 640
        )

        val result = engine.detect(frame)
        assertTrue(result is Result.Success)

        val detections = (result as Result.Success).data
        assertEquals(1, detections.size)
        assertEquals("carrot", detections.first().label)

        engine.release()
    }
}
