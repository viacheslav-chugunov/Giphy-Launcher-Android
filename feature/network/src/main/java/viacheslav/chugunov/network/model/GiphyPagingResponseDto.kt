package viacheslav.chugunov.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class GiphyPagingResponseDto(
    @SerialName("data")
    val data: List<GiphyObjectDto> = emptyList(),
    @SerialName("pagination")
    val pagination: GiphyPaginationDto = GiphyPaginationDto(),
    @SerialName("meta")
    val meta: GiphyMetaDto = GiphyMetaDto()
)