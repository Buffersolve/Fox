package ua.fox.navigation

import androidx.core.bundle.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavType
import androidx.navigation.toRoute
import io.ktor.util.decodeBase64Bytes
import io.ktor.util.encodeBase64
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ua.fox.data.model.FoxModel
import kotlin.reflect.typeOf

@Serializable
object Root

@Serializable
object Home

@Serializable
object Details

@Serializable
data class Viewer(val fox: FoxModel) {
    companion object {
        val typeMap = mapOf(typeOf<FoxModel>() to serializableType<FoxModel>())
        fun from(savedStateHandle: SavedStateHandle) = savedStateHandle.toRoute<Viewer>(typeMap)
    }
}

inline fun <reified T : Any> serializableType(
    isNullableAllowed: Boolean = false,
    json: Json = Json,
) = object : NavType<T>(isNullableAllowed = isNullableAllowed) {
    override fun get(bundle: Bundle, key: String) =
        bundle.getString(key)?.let<String, T>(json::decodeFromString)

    // TDOO
    fun base64UrlEncode(input: String): String {
        return input.encodeToByteArray().encodeBase64()
            .replace("+", "-")
            .replace("/", "_")
            .replace("=", "")
    }

    fun base64UrlDecode(input: String): String {
        val modifiedInput = input
            .replace("-", "+")
            .replace("_", "/")
            .padEnd(input.length + (4 - input.length % 4) % 4, '=')

        return modifiedInput.decodeBase64Bytes().decodeToString()
    }

    override fun serializeAsValue(value: T): String = base64UrlEncode(json.encodeToString(value))

    override fun parseValue(value: String): T = json.decodeFromString(base64UrlDecode(value))

    override fun put(bundle: Bundle, key: String, value: T) {
        bundle.putString(key, json.encodeToString(value))
    }
}