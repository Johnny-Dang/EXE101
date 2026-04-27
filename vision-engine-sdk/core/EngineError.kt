package com.visionengine.core

/**
 * Domain errors surfaced by the SDK. The API layer maps internal exceptions to these errors.
 */
sealed class EngineError(message: String, cause: Throwable? = null) : Exception(message, cause) {
    object NotInitialized : EngineError("Vision engine has not been initialized")
    object AlreadyReleased : EngineError("Vision engine has been released")
    object ModelLoadFailed : EngineError("Failed to load model")
    object InvalidFrame : EngineError("Invalid frame payload")
    object InferenceFailed : EngineError("Inference execution failed")
    data class Unknown(val details: String, val error: Throwable? = null) : EngineError(details, error)
}
