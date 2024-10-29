package com.example.dragonballwiki.core

import kotlin.coroutines.cancellation.CancellationException

inline fun <T> tryOrNull(
    block: () -> T,
): T? {
    return try {
        block()

    } catch (e: CancellationException) {
        throw e

    } catch (e: Exception) {
        null
    }
}