package ua.fox.data.model

import kotlinx.serialization.Serializable

@Serializable
data class FoxModel(
    val image: String,
    val link: String
)