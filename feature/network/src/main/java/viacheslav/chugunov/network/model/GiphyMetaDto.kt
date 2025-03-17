package viacheslav.chugunov.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @param message HTTP Response Message.
 * @param status HTTP Response Code.
 * @param responseId A unique ID paired with this response from the API.
 * @see <a href="https://developers.giphy.com/docs/api/schema/#meta-object">Documentation</a>
 * */
@Serializable
internal class GiphyMetaDto(
    @SerialName("msg")
    val message: String = "",
    @SerialName("status")
    val status: Int = 0,
    @SerialName("response_id")
    val responseId: String = ""
)