package com.visionengine.postprocessing

import com.visionengine.core.Detection
import com.visionengine.core.Frame
import com.visionengine.core.RectF

/**
 * Decodes flat YOLO-like tensor where each detection uses 6 values:
 * [x1, y1, x2, y2, confidence, classId].
 */
class YoloPostprocessor(
    private val confidenceThreshold: Float,
    iouThreshold: Float,
    private val labelMapper: LabelMapper = LabelMapper.coco80()
) : Postprocessor {
    private val nmsProcessor = NmsProcessor(iouThreshold)

    override fun postprocess(output: FloatArray, frame: Frame): List<Detection> {
        if (output.isEmpty()) return emptyList()

        val raw = mutableListOf<Detection>()
        var index = 0
        while (index + 5 < output.size) {
            val x1Raw = output[index]
            val y1Raw = output[index + 1]
            val x2Raw = output[index + 2]
            val y2Raw = output[index + 3]
            val confidence = output[index + 4]
            val classId = output[index + 5].toInt()

            if (confidence >= confidenceThreshold) {
                val (x1, y1, x2, y2) = scaleBox(x1Raw, y1Raw, x2Raw, y2Raw, frame)
                raw.add(
                    Detection(
                        label = labelMapper.map(classId),
                        confidence = confidence,
                        boundingBox = RectF(
                            left = x1,
                            top = y1,
                            right = x2,
                            bottom = y2
                        )
                    )
                )
            }

            index += 6
        }

        return nmsProcessor.apply(raw)
    }

    private fun scaleBox(x1: Float, y1: Float, x2: Float, y2: Float, frame: Frame): Box {
        val normalized = x1 <= 1f && y1 <= 1f && x2 <= 1f && y2 <= 1f
        val sx1 = if (normalized) x1 * frame.width else x1
        val sy1 = if (normalized) y1 * frame.height else y1
        val sx2 = if (normalized) x2 * frame.width else x2
        val sy2 = if (normalized) y2 * frame.height else y2

        return Box(
            x1 = sx1.coerceIn(0f, frame.width.toFloat()),
            y1 = sy1.coerceIn(0f, frame.height.toFloat()),
            x2 = sx2.coerceIn(0f, frame.width.toFloat()),
            y2 = sy2.coerceIn(0f, frame.height.toFloat())
        )
    }

    private data class Box(
        val x1: Float,
        val y1: Float,
        val x2: Float,
        val y2: Float
    )
}
