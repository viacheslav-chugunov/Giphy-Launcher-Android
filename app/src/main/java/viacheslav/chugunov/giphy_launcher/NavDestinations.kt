package viacheslav.chugunov.giphy_launcher

import kotlinx.serialization.Serializable
import viacheslav.chugunov.core.model.Gif

object NavDestinations {
    @Serializable
    data object GifsList

    @Serializable
    class GifDetails(val gif: Gif)

    @Serializable
    data object SearchGifs
}