package com.visionengine.core

/**
 * Represents a rectangle with floating-point coordinates.
 * We use a custom RectF to keep the core layer platform-independent (e.g., decoupled from android.graphics.RectF).
 */
data class RectF(
    val left: Float,
    val top: Float,
    val right: Float,
    val bottom: Float
) {
    val width: Float get() = right - left
    val height: Float get() = bottom - top
}
