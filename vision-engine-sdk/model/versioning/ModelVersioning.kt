package com.visionengine.model.versioning

data class ModelVersion(
    val version: String,
    val source: String,
    val checksum: String? = null
)

interface ModelVersioning {
    fun shouldUpdate(current: ModelVersion, available: ModelVersion): Boolean
}

class SemanticModelVersioning : ModelVersioning {
    override fun shouldUpdate(current: ModelVersion, available: ModelVersion): Boolean {
        return normalize(available.version) > normalize(current.version)
    }

    private fun normalize(raw: String): List<Int> {
        return raw.split('.')
            .map { it.toIntOrNull() ?: 0 }
            .let { parts ->
                if (parts.size >= 3) parts.take(3) else parts + List(3 - parts.size) { 0 }
            }
    }
}
