package com.visionengine.core

/**
 * Represents a single frame/image from the camera or other source.
 * 
 * To keep it platform-independent, the underlying data is kept as a generic Any or ByteArray,
 * and platform-specific implementations (like AndroidFrameProvider) will handle the actual conversion.
 * 
 * @property data The raw image data (e.g., ByteArray, NV21, or ByteBuffer).
 * @property width The width of the frame.
 * @property height The height of the frame.
 * @property rotation The rotation degrees (0, 90, 180, 270).
 */
data class Frame(
    val data: ByteArray, 
    val width: Int,
    val height: Int,
    val rotation: Int = 0,
    val format: ImageFormat = ImageFormat.NV21
)

enum class ImageFormat {
    NV21,
    YUV_420_888,
    RGB,
    RGBA
}
