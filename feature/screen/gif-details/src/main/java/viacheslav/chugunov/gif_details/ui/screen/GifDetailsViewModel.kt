package viacheslav.chugunov.gif_details.ui.screen

import viacheslav.chugunov.core.model.Gif
import viacheslav.chugunov.core.util.base.BaseViewModel

class GifDetailsViewModel(
    gif: Gif
) : BaseViewModel<GifDetailsState, GifDetailsAction>(GifDetailsState(gif)) {

    override fun handleAction(action: GifDetailsAction) {
        when (action) {
            is GifDetailsAction.SetGifFullscreen -> {
                state = state.copy(fullscreenGif = action.isFullscreen)
            }
        }
    }
}