package com.example.dragonballwiki.core

import android.util.Log
import kotlin.coroutines.cancellation.CancellationException

/**
 * Executes a block and returns null if an exception occurs.
 * 
 * IMPORTANT: This logs exceptions for debugging purposes.
 * CancellationException is re-thrown to preserve coroutine cancellation.
 * 
 * @param tag Optional tag for logging (defaults to "Utils")
 * @param block The block to execute
 * @return Result of block or null if exception occurs
 */
inline fun <T> tryOrNull(
    tag: String = "Utils",
    block: () -> T,
): T? {
    return try {
        block()
    } catch (e: CancellationException) {
        // Never catch CancellationException - it breaks coroutine cancellation
        throw e
    } catch (e: Exception) {
        Log.e(tag, "Exception in tryOrNull: ${e.message}", e)
        null
    }
}