package viacheslav.chugunov.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @param avatarUrl The URL for this user's avatar image.
 * @param bannerUrl The URL for the banner image that appears atop this user's profile page.
 * @param profileUrl The URL for this user's GIPHY profile.
 * @param username The username associated with this user.
 * @param displayName The display name associated with this user (contains formatting the base username might not).
 * @see <a href="https://developers.giphy.com/docs/api/schema/#user-object">Documentation</a>
 * */
@Serializable
internal class GiphyUserDto(
    @SerialName("avatar_url")
    val avatarUrl: String = "",
    @SerialName("banner_url")
    val bannerUrl: String = "",
    @SerialName("profile_url")
    val profileUrl: String = "",
    @SerialName("username")
    val username: String = "",
    @SerialName("display_name")
    val displayName: String = ""
)