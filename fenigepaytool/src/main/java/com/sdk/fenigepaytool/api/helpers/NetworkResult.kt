package com.sdk.fenigepaytool.api.helpers

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import retrofit2.HttpException
import java.net.SocketTimeoutException

internal sealed class NetworkResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : NetworkResult<T>()
    data class Error(val exception: Exception) : NetworkResult<Nothing>()
}

internal suspend fun <T : Any> safeApiResult(call: suspend () -> T): NetworkResult<T> {
    return try {
        val response = call.invoke()
        return NetworkResult.Success(response)
    } catch (e: HttpException) {
        e.printStackTrace()
        runCatching {
            e.response()?.errorBody()?.string()?.let {
                return NetworkResult.Error(ServerError(""))
            }
        }
        NetworkResult.Error(e)
    } catch (e: SocketTimeoutException) {
        e.printStackTrace()
        NetworkResult.Error(e)
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
        NetworkResult.Error(e)
    }
}

@JsonClass(generateAdapter = true)
internal data class ApiError(
    @Json(name = "error") val message: String
)

internal class ServerError(val localizedErrorMessage: String) : Exception()
