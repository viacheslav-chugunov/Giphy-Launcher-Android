package viacheslav.chugunov.search_gifs.ui.screen

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import viacheslav.chugunov.core.repository.GifsNetworkRepository
import viacheslav.chugunov.core.util.AsyncResource
import viacheslav.chugunov.core.util.base.BaseViewModel

@OptIn(FlowPreview::class)
class SearchGifsViewModel(
    private val gifsNetworkRepository: GifsNetworkRepository
) : BaseViewModel<SearchGifsState, SearchGifsAction>(SearchGifsState()) {
    private val queryFlow = MutableSharedFlow<String>(replay = 1)

    init {
        viewModelScope.launch {
            queryFlow
                .onEach { query ->
                    state = state.copy(queryProcessing = true)
                    if (query.isBlank()) searchGifs()
                }
                .debounce(3000)
                .onEach {
                    state = state.copy(queryProcessing = false)
                }
                .collectLatest { _ ->
                    searchGifs()
                }
        }
    }

    override fun handleAction(action: SearchGifsAction) {
        when (action) {
            SearchGifsAction.RetrySearch -> {
                searchGifs()
            }
            is SearchGifsAction.Search -> {
                state = state.copy(query = action.query)
                queryFlow.tryEmit(action.query)
            }
        }
    }

    private fun searchGifs() {
        val query = state.query
        if (query.isBlank()) {
            state = state.copy(asyncGifs = AsyncResource.Success(emptyList()))
            return
        }
        viewModelScope.launch {
            state = state.copy(asyncGifs = AsyncResource.Loading())
            val result = gifsNetworkRepository.search(query, 50, 0)
            state = state.copy(asyncGifs = result.map { it.gifs })
        }
    }
}