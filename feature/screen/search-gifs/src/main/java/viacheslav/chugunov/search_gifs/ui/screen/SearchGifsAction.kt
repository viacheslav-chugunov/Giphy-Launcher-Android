package viacheslav.chugunov.search_gifs.ui.screen

sealed interface SearchGifsAction {
    data object RetrySearch : SearchGifsAction
    @JvmInline value class Search(val query: String) : SearchGifsAction
    @JvmInline value class RequestNewGifs(val lastVisibleIndex: Int): SearchGifsAction
}