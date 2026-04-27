package com.visionengine.model.loader

import java.nio.ByteBuffer

interface ModelLoader {
    suspend fun load(modelPath: String): ByteBuffer
}
