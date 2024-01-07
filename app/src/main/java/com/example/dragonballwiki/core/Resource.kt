package com.example.dragonballwiki.core

import com.example.dragonballwiki.core.Resource.Error
import retrofit2.Response


sealed class Resource<T>(
    val data: T? = null, val message: String? = null,
    val errorResponse: ErrorResponse? = null
) {
    class Success<T>(data: T) : Resource<T>(data, null, null)
    open class Error<T>(message: String) : Resource<T>(null, message, null)
    class ErrorsResponse<T>(errorResponse: ErrorResponse?) : Resource<T>(null, null, errorResponse)
}

class NetWorkError<T>(message: String) : Error<T>(message)