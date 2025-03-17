package viacheslav.chugunov.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @param type By default, this is almost always GIF.
 * @param id This GIF's unique ID
 * @param slug The unique slug used in this GIF's URL
 * @param url The unique URL for this GIF
 * @param bitlyUrl The unique bit.ly URL for this GIF
 * @param embedUrl A URL used for embedding this GIF
 * @param username The username this GIF is attached to, if applicable
 * @param source The page on which this GIF was found
 * @param rating The MPAA-style rating for this content. Examples include Y, G, PG, PG-13 and R
 * @param contentUrl Currently unused
 * @param user An object containing data about the user associated with this GIF, if applicable.
 * @param sourceTld The top level domain of the source URL.
 * @param sourcePostUrl The URL of the webpage on which this GIF was found.
 * @param updateDatetime The date on which this GIF was last updated.
 * @param createDatetime The date this GIF was added to the GIPHY database.
 * @param importDatetime The creation or upload date from this GIF's source.
 * @param trendingDatetime The date on which this gif was marked trending, if applicable.
 * @param title The title that appears on giphy.com for this GIF.
 * @param altText Alt text enables assistive programs to read descriptions of GIFs.
 * @see <a href="https://developers.giphy.com/docs/api/schema/#gif-object">Documentation</a>
 * */
@Serializable
internal class GiphyObjectDto(
    @SerialName("type")
    val type: String = "",
    @SerialName("id")
    val id: String = "",
    @SerialName("slug")
    val slug: String = "",
    @SerialName("url")
    val url: String = "",
    @SerialName("bitly_url")
    val bitlyUrl: String = "",
    @SerialName("embed_url")
    val embedUrl: String = "",
    @SerialName("username")
    val username: String = "",
    @SerialName("source")
    val source: String = "",
    @SerialName("rating")
    val rating: String = "",
    @SerialName("content_url")
    val contentUrl: String = "",
    @SerialName("user")
    val user: GiphyUserDto = GiphyUserDto(),
    @SerialName("source_tld")
    val sourceTld: String = "",
    @SerialName("source_post_url")
    val sourcePostUrl: String = "",
    @SerialName("update_datetime")
    val updateDatetime: String = "",
    @SerialName("create_datetime")
    val createDatetime: String = "",
    @SerialName("import_datetime")
    val importDatetime: String = "",
    @SerialName("trending_datetime")
    val trendingDatetime: String = "",
    @SerialName("title")
    val title: String = "",
    @SerialName("alt_text")
    val altText: String = ""
)