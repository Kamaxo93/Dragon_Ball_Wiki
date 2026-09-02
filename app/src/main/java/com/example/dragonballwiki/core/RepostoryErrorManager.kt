package com.example.dragonballwiki.core

import android.util.Log
import com.example.dragonballwiki.core.async.AsyncError
import com.example.dragonballwiki.core.async.AsyncResult
import com.google.gson.Gson
import com.google.gson.JsonParseException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.coroutines.cancellation.CancellationException

object RepositoryErrorManager {
    
    private const val TAG = "ErrorManager"

    suspend fun <T> wrap(block: suspend () -> T): Flow<AsyncResult<T>> =
        flow {
            Log.d(TAG, "🔒 ErrorManager.wrap() - Emitting Loading state")
            emit(AsyncResult.Loading())
            try {
                Log.d(TAG, "🚀 ErrorManager.wrap() - Executing network block")
                val result = block()
                Log.i(TAG, "✅ ErrorManager.wrap() - Block executed successfully")
                emit(AsyncResult.Success(result))

            } catch (e: CancellationException) {
                // CancellationException should never be intercepted, throw it as-is
                Log.w(TAG, "⚠️ CancellationException caught - Re-throwing", e)
                throw e

            } catch (e: Exception) {
                val asyncError = e.toAsyncError()
                Log.e(TAG, "❌ ErrorManager.wrap() - Exception caught: ${e::class.simpleName}", e)
                Log.e(TAG, "❌ Converted to AsyncError: $asyncError")
                emit(AsyncResult.Error(asyncError))
            }
        }

    fun Exception.toAsyncError(): AsyncError {
        return when (this) {
            is HttpException -> AsyncError.ServerError(
                code = code(),
                errorMessage = response()?.errorBody()?.getErrorMessage(),
            )

            is UnknownHostException -> AsyncError.ConnectionError
            is SocketTimeoutException -> AsyncError.TimeoutError
            is JsonParseException -> AsyncError.DataParseError
            else -> AsyncError.UnknownError(this)
        }
    }

    private fun ResponseBody.getErrorMessage(): String? =
        tryOrNull {
            Gson().fromJson(charStream(), GenericErrorDto::class.java).message
        }

}