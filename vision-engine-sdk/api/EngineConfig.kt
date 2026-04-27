package com.visionengine.api

/**
 * Configuration for initializing the VisionEngine.
 * 
 * @property modelPath The path to the model file (e.g., in assets or local storage).
 * @property confidenceThreshold The minimum confidence threshold to keep a detection.
 * @property inputWidth The expected input width of the model.
 * @property inputHeight The expected input height of the model.
 * @property useGPU Whether to use GPU acceleration if available.
 */
data class EngineConfig(
    val modelPath: String,
    val modelVersion: String = "1.0.0",
    val confidenceThreshold: Float = 0.5f,
    val iouThreshold: Float = 0.45f,
    val inputWidth: Int = 640,
    val inputHeight: Int = 640,
    val useGPU: Boolean = false,
    val useNNAPI: Boolean = false,
    val numThreads: Int = 2
)
