package com.visionengine.api

import com.visionengine.core.Detection
import com.visionengine.core.Frame
import com.visionengine.core.Result

/**
 * The main public interface of the Vision Engine SDK.
 * Mobile apps will interact with this interface.
 */
interface VisionEngine {
    /**
     * Initializes the engine with the provided configuration.
     */
    suspend fun init(config: EngineConfig)

    /**
     * Performs object detection on a given frame.
     * 
     * @param frame The input frame.
     * @return A list of detections found in the frame.
     */
    suspend fun detect(frame: Frame): Result<List<Detection>>

    /**
     * Releases any resources (e.g., models, threads, memory) held by the engine.
     */
    fun release()
}
