package com.example.dragonballwiki.dragonlist.data.repository

/**
 * Custom exception for repository layer errors.
 * Provides descriptive error messages for debugging.
 */
class RepositoryException(
    message: String,
    cause: Throwable? = null
) : Exception(message, cause)
