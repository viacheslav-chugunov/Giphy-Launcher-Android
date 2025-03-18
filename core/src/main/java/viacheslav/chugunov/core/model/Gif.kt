package viacheslav.chugunov.core.model

import kotlinx.serialization.Serializable

@Serializable
class Gif(
    val id: String,
    val previewUrl: String,
    val fullUrl: String,
    val username: String,
    val createdAt: String,
    val title: String
) {

    override fun equals(other: Any?): Boolean = other is Gif && id == other.id

    override fun hashCode(): Int = id.hashCode()
}