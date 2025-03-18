package viacheslav.chugunov.gifs_list.ui.screen

sealed interface GifsListAction {
    data object RetryToLoadGifs : GifsListAction
    @JvmInline value class RequestNewGifs(val lastVisibleIndex: Int): GifsListAction
}