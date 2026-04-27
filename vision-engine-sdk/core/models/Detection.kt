package com.visionengine.core

/**
 * Represents an object detection result.
 * 
 * @property label The class label of the detected object (e.g., "person", "car").
 * @property confidence The confidence score of the detection [0.0 - 1.0].
 * @property boundingBox The bounding box of the detected object.
 */
data class Detection(
    val label: String,
    val confidence: Float,
    val boundingBox: RectF
)
