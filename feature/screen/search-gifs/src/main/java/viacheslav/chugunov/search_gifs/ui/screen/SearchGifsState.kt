package viacheslav.chugunov.search_gifs.ui.screen

import viacheslav.chugunov.core.model.Gif
import viacheslav.chugunov.core.util.AsyncResource

data class SearchGifsState(
    val query: String = "",
    val queryProcessing: Boolean = false,
    val asyncGifs: AsyncResource<List<Gif>> = AsyncResource.Success(emptyList())
) {
}