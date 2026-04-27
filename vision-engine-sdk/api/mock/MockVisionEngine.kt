package com.visionengine.api.mock

import com.visionengine.api.EngineConfig
import com.visionengine.api.VisionEngine
import com.visionengine.core.Detection
import com.visionengine.core.Frame
import com.visionengine.core.RectF
import com.visionengine.core.Result

class MockVisionEngine : VisionEngine {
    private var initialized: Boolean = false

    override suspend fun init(config: EngineConfig) {
        initialized = true
    }

    override suspend fun detect(frame: Frame): Result<List<Detection>> {
        if (!initialized) {
            return Result.Error(IllegalStateException("Mock engine not initialized"))
        }

        val fake = Detection(
            label = "carrot",
            confidence = 0.95f,
            boundingBox = RectF(left = 0.1f, top = 0.1f, right = 0.4f, bottom = 0.6f)
        )
        return Result.Success(listOf(fake))
    }

    override fun release() {
        initialized = false
    }
}
