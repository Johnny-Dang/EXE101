package com.visionengine.engine.pipeline

import com.visionengine.core.Detection
import com.visionengine.core.Frame
import com.visionengine.inference.InferenceEngine
import com.visionengine.postprocessing.Postprocessor
import com.visionengine.preprocessing.Preprocessor

class DetectionPipeline(
    private val preprocessor: Preprocessor,
    private val inferenceEngine: InferenceEngine,
    private val postprocessor: Postprocessor
) {
    suspend fun run(frame: Frame): List<Detection> {
        val input = preprocessor.preprocess(frame)
        val output = inferenceEngine.infer(input)
        return postprocessor.postprocess(output, frame)
    }
}
