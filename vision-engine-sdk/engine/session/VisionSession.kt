package com.visionengine.engine.session

import com.visionengine.api.EngineConfig
import com.visionengine.core.Detection
import com.visionengine.core.EngineError
import com.visionengine.core.Frame
import com.visionengine.core.Result
import com.visionengine.engine.pipeline.DetectionPipeline

class VisionSession(
    private val pipeline: DetectionPipeline,
    private val modelInitializer: suspend (EngineConfig) -> Unit,
    private val modelReleaser: () -> Unit
) {
    @Volatile
    private var initialized: Boolean = false

    suspend fun init(config: EngineConfig) {
        modelInitializer(config)
        initialized = true
    }

    suspend fun detect(frame: Frame): Result<List<Detection>> {
        if (!initialized) return Result.Error(EngineError.NotInitialized)
        return try {
            Result.Success(pipeline.run(frame))
        } catch (error: Exception) {
            Result.Error(EngineError.Unknown("Detection failed", error))
        }
    }

    fun release() {
        modelReleaser()
        initialized = false
    }
}
