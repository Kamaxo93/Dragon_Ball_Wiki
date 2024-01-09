package com.example.dragonballwiki.core

import com.example.dragonballwiki.core.Resource.Error
import com.google.gson.Gson
import retrofit2.Response
import java.lang.Exception


sealed class Resource<T>(
    val data: T? = null, val message: String? = null,
    val errorResponse: ErrorResponse? = null
) {
    class Success<T>(data: T) : Resource<T>(data, null, null)
    open class Error<T>(message: String) : Resource<T>(null, message, null)
    class ErrorsResponse<T>(errorResponse: ErrorResponse?) : Resource<T>(null, null, errorResponse)
}

class NetWorkError<T>(message: String) : Error<T>(message)


fun <T> responseService(response: Response<T>): Resource<T> {
    return try {
        if (response.isSuccessful &&
            response.body() != null
        ) {
            Resource.Success((response.body()!!))
        } else {
            Resource.ErrorsResponse(
                Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
            )
        }
    } catch (e: Exception) {
        Error(e.message ?: "en el servicio")
    }
}