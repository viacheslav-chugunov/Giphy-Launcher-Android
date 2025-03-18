package viacheslav.chugunov.gifs_list.ui.screen

import viacheslav.chugunov.core.model.Gif
import viacheslav.chugunov.core.util.AsyncResource

data class GifsListState(
    val asyncGifs: AsyncResource<List<Gif>> = AsyncResource.Loading(),
    val activeGifsPaging: Boolean = false
)