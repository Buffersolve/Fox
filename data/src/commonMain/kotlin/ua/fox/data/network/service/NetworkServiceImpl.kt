package ua.fox.data.network.service

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.json.Json
import ua.fox.data.model.FoxModel

private val httpClient = HttpClient {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
        })
    }
}

internal class NetworkServiceImpl : NetworkService {

    companion object {
        private const val BASE_URL = "https://randomfox.ca/"
        private const val FLOOF = "floof"
    }

    override suspend fun getFox(): ApiResult<FoxModel> {
        try {
            httpClient.get(BASE_URL + FLOOF).body<FoxModel>().also {
                return ApiResult.Success(it)
            }
        } catch (e: Exception) {
            currentCoroutineContext().ensureActive()
            e.printStackTrace()
            return ApiResult.Error(e.message)
        }
    }

}