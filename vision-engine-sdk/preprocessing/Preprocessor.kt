package com.visionengine.preprocessing

import com.visionengine.core.Frame
import java.nio.ByteBuffer

interface Preprocessor {
    fun preprocess(frame: Frame): ByteBuffer
}
