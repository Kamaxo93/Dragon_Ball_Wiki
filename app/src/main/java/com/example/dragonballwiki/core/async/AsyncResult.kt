package com.example.dragonballwiki.core.async

sealed class AsyncResult<T> {
    class Loading<T> : AsyncResult<T>()
    class Success<T>(val data: T) : AsyncResult<T>()
    class Error<T>(val error: AsyncError) : AsyncResult<T>()
}

inline fun <T, R> AsyncResult<T>.map(transform: (T) -> R): AsyncResult<R> {
    return when (this) {
        is AsyncResult.Loading -> AsyncResult.Loading()
        is AsyncResult.Success -> AsyncResult.Success(transform(this.data))
        is AsyncResult.Error -> AsyncResult.Error(this.error)
    }
}