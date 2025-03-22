package viacheslav.chugunov.search_gifs.ui.screen

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import viacheslav.chugunov.core.model.Paging
import viacheslav.chugunov.core.repository.GifsNetworkRepository
import viacheslav.chugunov.core.util.AsyncResource
import viacheslav.chugunov.core.util.CoroutineDispatchers
import viacheslav.chugunov.core.util.base.BaseViewModel

@OptIn(FlowPreview::class)
class SearchGifsViewModel(
    private val gifsNetworkRepository: GifsNetworkRepository,
    private val coroutineDispatchers: CoroutineDispatchers
) : BaseViewModel<SearchGifsState, SearchGifsAction>(SearchGifsState()) {
    private val queryFlow = MutableSharedFlow<String>(replay = 1)
    private var isGifsLoading: Boolean = false
    private var gifsPaging: Paging = Paging.EMPTY
    private val loadGifsMutex = Mutex()

    init {
        viewModelScope.launch {
            queryFlow
                .onEach { query ->
                    state = state.copy(queryProcessing = true)
                }
                .debounce {
                    if (state.query.isNotBlank()) {
                        1500
                    } else {
                        0
                    }
                }
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
            is SearchGifsAction.RequestNewGifs -> {
                val shownGifs = state.asyncGifs.dataOrNull ?: emptyList()
                if (action.lastVisibleIndex >= shownGifs.size - 1) {
                    searchGifs()
                }
            }
        }
    }

    private fun searchGifs() {
        val query = state.query
        if (query.isBlank()) {
            state = state.copy(asyncGifs = AsyncResource.Success(emptyList()))
            return
        }
        viewModelScope.launch(coroutineDispatchers.io) {
            loadGifsMutex.withLock {
                if (isGifsLoading) return@launch
                val offset = gifsPaging.got
                if (offset >= 5000) return@launch
                state = state.copy(activeGifsPaging = true)
                isGifsLoading = true
                val asyncPagingGifs = gifsNetworkRepository.search(query, 50,  offset)
                when (asyncPagingGifs) {
                    is AsyncResource.Failure -> {
                        val error = asyncPagingGifs.error
                        state = state.copy(
                            asyncGifs = AsyncResource.Failure(error),
                            activeGifsPaging = false
                        )
                    }
                    is AsyncResource.Success -> {
                        val oldGifs = state.asyncGifs.dataOrNull ?: emptyList()
                        val newGifs = asyncPagingGifs.data.gifs
                        gifsPaging = asyncPagingGifs.data.paging
                        val actualGifs = oldGifs.toMutableSet()
                        actualGifs.addAll(newGifs)
                        state = state.copy(
                            asyncGifs = AsyncResource.Success(actualGifs.toList()),
                            activeGifsPaging = false
                        )
                    }
                    else -> {}
                }
                isGifsLoading = false
            }
        }
    }
}