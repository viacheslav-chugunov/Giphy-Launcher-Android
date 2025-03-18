package viacheslav.chugunov.gif_details.ui.screen

sealed interface GifDetailsAction {
    @JvmInline value class SetGifFullscreen(val isFullscreen: Boolean) : GifDetailsAction
}