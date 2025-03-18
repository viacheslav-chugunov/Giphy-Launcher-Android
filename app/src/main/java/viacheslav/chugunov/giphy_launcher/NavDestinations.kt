package viacheslav.chugunov.giphy_launcher

import kotlinx.serialization.Serializable

object NavDestinations {
    @Serializable
    data object GifsList

    @Serializable
    data object GifDetails

    @Serializable
    data object SearchGifs
}