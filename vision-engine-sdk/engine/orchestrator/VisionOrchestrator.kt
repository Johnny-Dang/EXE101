package com.visionengine.engine.orchestrator

import com.visionengine.core.Detection
import com.visionengine.core.Frame
import com.visionengine.core.Result
import com.visionengine.engine.session.VisionSession

class VisionOrchestrator(
    private val session: VisionSession
) {
    suspend fun process(frame: Frame): Result<List<Detection>> {
        return session.detect(frame)
    }
}
