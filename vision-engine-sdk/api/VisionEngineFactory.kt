package com.visionengine.api

import com.visionengine.api.mock.MockVisionEngine
import com.visionengine.inference.HeuristicInferenceEngine
import com.visionengine.postprocessing.YoloPostprocessor
import com.visionengine.preprocessing.StandardImagePreprocessor

/**
 * Factory to create instances of VisionEngine.
 * This hides the internal implementation details from the user.
 */
object VisionEngineFactory {
    
    /**
     * Creates and returns a new instance of VisionEngine.
     */
    fun create(): VisionEngine {
        return DefaultVisionEngine(
            preprocessor = StandardImagePreprocessor(),
            inferenceEngine = HeuristicInferenceEngine(),
            postprocessorFactory = { config ->
                YoloPostprocessor(
                    confidenceThreshold = config.confidenceThreshold,
                    iouThreshold = config.iouThreshold
                )
            }
        )
    }

    fun createMock(): VisionEngine {
        return MockVisionEngine()
    }
}

