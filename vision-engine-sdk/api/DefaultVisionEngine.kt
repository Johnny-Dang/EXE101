package com.visionengine.api

import com.visionengine.core.Detection
import com.visionengine.core.Frame
import com.visionengine.core.Result
import com.visionengine.engine.orchestrator.VisionOrchestrator
import com.visionengine.engine.pipeline.DetectionPipeline
import com.visionengine.engine.session.VisionSession
import com.visionengine.inference.InferenceEngine
import com.visionengine.postprocessing.Postprocessor
import com.visionengine.preprocessing.Preprocessor

/**
 * Early default implementation with pluggable pipeline components.
 * Concrete preprocess/infer/post implementations will replace the placeholder defaults in next steps.
 */
class DefaultVisionEngine(
    private val preprocessor: Preprocessor,
    private val inferenceEngine: InferenceEngine,
    private val postprocessorFactory: (EngineConfig) -> Postprocessor
) : VisionEngine {
    private var session: VisionSession? = null
    private var orchestrator: VisionOrchestrator? = null
    private lateinit var config: EngineConfig

    private fun buildPipeline(config: EngineConfig): DetectionPipeline {
        val postprocessor = postprocessorFactory(config)
        return DetectionPipeline(
            preprocessor = preprocessor,
            inferenceEngine = inferenceEngine,
            postprocessor = postprocessor
        )
    }

    private fun createSession(config: EngineConfig): VisionSession {
        val pipeline = buildPipeline(config)
        return VisionSession(
            pipeline = pipeline,
            modelInitializer = { cfg -> inferenceEngine.load(cfg.modelPath) },
            modelReleaser = { inferenceEngine.release() }
        )
    }

    override suspend fun init(config: EngineConfig) {
        this.config = config
        val localSession = createSession(config)
        localSession.init(config)
        session = localSession
        orchestrator = VisionOrchestrator(localSession)
    }

    override suspend fun detect(frame: Frame): Result<List<Detection>> {
        val localOrchestrator = orchestrator
            ?: return Result.Error(IllegalStateException("Engine is not initialized"))
        return localOrchestrator.process(frame)
    }

    override fun release() {
        session?.release()
        session = null
        orchestrator = null
    }
}
