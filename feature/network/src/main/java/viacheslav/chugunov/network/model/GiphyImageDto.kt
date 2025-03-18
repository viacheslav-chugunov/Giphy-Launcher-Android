package viacheslav.chugunov.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @param url The publicly-accessible direct URL for this GIF.
 * @param width The width of this GIF in pixels.
 * @param height The height of this GIF in pixels.
 * @param size The size of this GIF in bytes.
 * @param mp4 The URL for this GIF in .MP4 format.
 * @param mp4Size The size in bytes of the .MP4 file corresponding to this GIF.
 * @param webp The URL for this GIF in .webp format.
 * @param webpSize The size in bytes of the .webp file corresponding to this GIF.
 * @see <a href="https://developers.giphy.com/docs/api/schema#image-object">Documentation</a>
 * */
@Serializable
class GiphyImageDto(
    @SerialName("url")
    val url: String = "",
    @SerialName("width")
    val width: String = "",
    @SerialName("height")
    val height: String = "",
    @SerialName("size")
    val size: String = "",
    @SerialName("mp4")
    val mp4: String = "",
    @SerialName("mp4_size")
    val mp4Size: String = "",
    @SerialName("webp")
    val webp: String = "",
    @SerialName("webp_size")
    val webpSize: String = ""
)