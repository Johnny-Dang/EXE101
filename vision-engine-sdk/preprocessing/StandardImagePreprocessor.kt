package com.visionengine.preprocessing

import com.visionengine.core.Frame
import com.visionengine.core.ImageFormat
import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * Converts raw frame bytes into a normalized float tensor layout [1, H, W, 3].
 * This implementation is platform-agnostic and avoids Bitmap usage.
 */
class StandardImagePreprocessor(
    private val inputWidth: Int = 640,
    private val inputHeight: Int = 640
) : Preprocessor {

    override fun preprocess(frame: Frame): ByteBuffer {
        val buffer = ByteBuffer
            .allocateDirect(inputWidth * inputHeight * 3 * 4)
            .order(ByteOrder.nativeOrder())

        for (y in 0 until inputHeight) {
            val srcY = y * frame.height / inputHeight
            for (x in 0 until inputWidth) {
                val srcX = x * frame.width / inputWidth
                val (r, g, b) = readRgbAt(frame, srcX, srcY)
                buffer.putFloat(r / 255f)
                buffer.putFloat(g / 255f)
                buffer.putFloat(b / 255f)
            }
        }

        buffer.rewind()
        return buffer
    }

    private fun readRgbAt(frame: Frame, x: Int, y: Int): Triple<Float, Float, Float> {
        return when (frame.format) {
            ImageFormat.RGB -> {
                val index = ((y * frame.width) + x) * 3
                val r = frame.data.getOrElse(index) { 0 }.toUByte().toFloat()
                val g = frame.data.getOrElse(index + 1) { 0 }.toUByte().toFloat()
                val b = frame.data.getOrElse(index + 2) { 0 }.toUByte().toFloat()
                Triple(r, g, b)
            }

            ImageFormat.RGBA -> {
                val index = ((y * frame.width) + x) * 4
                val r = frame.data.getOrElse(index) { 0 }.toUByte().toFloat()
                val g = frame.data.getOrElse(index + 1) { 0 }.toUByte().toFloat()
                val b = frame.data.getOrElse(index + 2) { 0 }.toUByte().toFloat()
                Triple(r, g, b)
            }

            ImageFormat.NV21, ImageFormat.YUV_420_888 -> {
                // Platform adapters should provide RGB/RGBA for best fidelity. Here we fallback to Y plane.
                val yIndex = y * frame.width + x
                val luma = frame.data.getOrElse(yIndex) { 0 }.toUByte().toFloat()
                Triple(luma, luma, luma)
            }
        }
    }
}
