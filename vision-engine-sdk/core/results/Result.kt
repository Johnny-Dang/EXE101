package com.visionengine.core

/**
 * A wrapper to encapsulate successful outcomes or errors.
 */
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}
