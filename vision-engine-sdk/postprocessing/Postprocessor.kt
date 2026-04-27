package com.visionengine.postprocessing

import com.visionengine.core.Detection
import com.visionengine.core.Frame

interface Postprocessor {
    fun postprocess(output: FloatArray, frame: Frame): List<Detection>
}
