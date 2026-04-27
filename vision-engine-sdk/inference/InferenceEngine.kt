package com.visionengine.inference

import java.nio.ByteBuffer

interface InferenceEngine {
    suspend fun load(modelPath: String)
    suspend fun infer(input: ByteBuffer): FloatArray
    fun release()
}
