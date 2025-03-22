package viacheslav.chugunov.search_gifs.ui.screen

sealed interface SearchGifsAction {
    data object RetrySearch : SearchGifsAction
    @JvmInline value class Search(val query: String) : SearchGifsAction
    data object RequestNewGifs: SearchGifsAction
}