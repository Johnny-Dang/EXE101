package com.visionengine.api

import com.visionengine.core.Detection
import com.visionengine.core.Frame
import com.visionengine.inference.InferenceEngine
import com.visionengine.postprocessing.Postprocessor
import com.visionengine.preprocessing.Preprocessor
import java.nio.ByteBuffer
import java.nio.ByteOrder

internal class NoOpPreprocessor : Preprocessor {
    override fun preprocess(frame: Frame): ByteBuffer {
        val size = frame.data.size.coerceAtLeast(1)
        val buffer = ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder())
        buffer.put(frame.data)
        buffer.rewind()
        return buffer
    }
}

internal class NoOpInferenceEngine : InferenceEngine {
    override suspend fun load(modelPath: String) {
        // Placeholder for Step 8 TFLite loading.
    }

    override suspend fun infer(input: ByteBuffer): FloatArray {
        // Placeholder tensor output for Step 9 postprocessing.
        return floatArrayOf(0.1f, 0.1f, 0.4f, 0.6f, 0.92f, 0f)
    }

    override fun release() {
        // Placeholder for interpreter release.
    }
}

internal class NoOpPostprocessor : Postprocessor {
    override fun postprocess(output: FloatArray, frame: Frame): List<Detection> {
        return emptyList()
    }
}
