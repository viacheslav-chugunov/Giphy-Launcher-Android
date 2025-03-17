package viacheslav.chugunov.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @see <a href="https://developers.giphy.com/docs/api/endpoint#search">Documentation</a>
 * */
@Serializable
internal class GiphySearchResponseDto(
    @SerialName("data")
    val data: List<GiphyObjectDto> = emptyList(),
    @SerialName("pagination")
    val pagination: GiphyPaginationDto,
    @SerialName("meta")
    val meta: GiphyMetaDto = GiphyMetaDto()
)