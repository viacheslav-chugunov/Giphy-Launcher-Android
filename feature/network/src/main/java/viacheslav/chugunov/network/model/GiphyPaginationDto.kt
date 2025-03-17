package viacheslav.chugunov.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @param offset Position in pagination.
 * @param totalCount Total number of items available (not returned on every endpoint).
 * @param count Total number of items returned.
 * @see <a href="https://developers.giphy.com/docs/api/schema/#pagination-object">Documentation</a>
 * */
@Serializable
internal class GiphyPaginationDto(
    @SerialName("offset")
    val offset: Int = 0,
    @SerialName("total_count")
    val totalCount: Int = 0,
    @SerialName("count")
    val count: Int = 0
)