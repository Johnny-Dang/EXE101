package com.visionengine.postprocessing

import com.visionengine.core.Detection
import kotlin.math.max
import kotlin.math.min

class NmsProcessor(
    private val iouThreshold: Float
) {
    fun apply(detections: List<Detection>): List<Detection> {
        if (detections.isEmpty()) return emptyList()

        val sorted = detections.sortedByDescending { it.confidence }.toMutableList()
        val selected = mutableListOf<Detection>()

        while (sorted.isNotEmpty()) {
            val best = sorted.removeAt(0)
            selected.add(best)
            sorted.removeAll { candidate -> iou(best, candidate) > iouThreshold }
        }

        return selected
    }

    private fun iou(a: Detection, b: Detection): Float {
        val left = max(a.boundingBox.left, b.boundingBox.left)
        val top = max(a.boundingBox.top, b.boundingBox.top)
        val right = min(a.boundingBox.right, b.boundingBox.right)
        val bottom = min(a.boundingBox.bottom, b.boundingBox.bottom)

        val w = (right - left).coerceAtLeast(0f)
        val h = (bottom - top).coerceAtLeast(0f)
        val inter = w * h

        val areaA = a.boundingBox.width.coerceAtLeast(0f) * a.boundingBox.height.coerceAtLeast(0f)
        val areaB = b.boundingBox.width.coerceAtLeast(0f) * b.boundingBox.height.coerceAtLeast(0f)
        val union = areaA + areaB - inter

        if (union <= 0f) return 0f
        return inter / union
    }
}
