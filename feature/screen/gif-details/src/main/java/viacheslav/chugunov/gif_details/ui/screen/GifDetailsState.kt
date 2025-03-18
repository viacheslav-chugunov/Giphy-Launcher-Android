package viacheslav.chugunov.gif_details.ui.screen

import viacheslav.chugunov.core.model.Gif

data class GifDetailsState(
    val gif: Gif,
    val fullscreenGif: Boolean = false
)