package com.visionengine.model.repository

import com.visionengine.model.loader.ModelLoader
import com.visionengine.model.versioning.ModelVersion
import java.nio.ByteBuffer

interface ModelRepository {
    suspend fun resolveCurrentVersion(): ModelVersion
    suspend fun loadModel(version: ModelVersion): ByteBuffer
}

class DefaultModelRepository(
    private val loader: ModelLoader,
    private val modelPathResolver: (ModelVersion) -> String
) : ModelRepository {
    override suspend fun resolveCurrentVersion(): ModelVersion {
        return ModelVersion(version = "1.0.0", source = "local")
    }

    override suspend fun loadModel(version: ModelVersion): ByteBuffer {
        return loader.load(modelPathResolver(version))
    }
}
