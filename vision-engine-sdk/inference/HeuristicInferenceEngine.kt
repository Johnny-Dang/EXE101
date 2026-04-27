package com.visionengine.inference

import java.nio.ByteBuffer

/**
 * Temporary inference implementation that emits deterministic YOLO-like tensors.
 * Replace by TFLiteInferenceEngine in Step 8.
 */
class HeuristicInferenceEngine : InferenceEngine {
    private var initialized = false

    override suspend fun load(modelPath: String) {
        initialized = true
    }

    override suspend fun infer(input: ByteBuffer): FloatArray {
        if (!initialized) return emptyArray

        // Two overlapping detections (same class) so NMS behavior is verifiable.
        return floatArrayOf(
            0.12f, 0.10f, 0.45f, 0.62f, 0.93f, 51f,
            0.14f, 0.12f, 0.43f, 0.60f, 0.87f, 51f,
            0.56f, 0.20f, 0.85f, 0.65f, 0.49f, 2f
        )
    }

    override fun release() {
        initialized = false
    }

    private companion object {
        val emptyArray = floatArrayOf()
    }
}
