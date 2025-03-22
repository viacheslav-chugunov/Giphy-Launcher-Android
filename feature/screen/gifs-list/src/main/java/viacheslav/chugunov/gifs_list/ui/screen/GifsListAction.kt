package viacheslav.chugunov.gifs_list.ui.screen

sealed interface GifsListAction {
    data object RetryToLoadGifs : GifsListAction
    data object RequestNewGifs: GifsListAction
}