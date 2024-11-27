package ua.fox.data.network.service

import kotlinx.serialization.Serializable

@Serializable
sealed class ApiResult<T>(
    val data: T? = null,
    val error: String? = null
) {
    class Success<T>(quotes: T) : ApiResult<T>(data = quotes)
    class Error<T>(error: String?) : ApiResult<T>(error = error)
}