package viacheslav.chugunov.giphy_launcher.mock

import viacheslav.chugunov.core.model.Gif

fun MockGif(position: Int): Gif = Gif(
    id = position.toString(),
    previewUrl = position.toString(),
    fullUrl = position.toString(),
    username = position.toString(),
    createdAt = position.toString(),
    title = position.toString()
)