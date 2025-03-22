package viacheslav.chugunov.gifs_list.ui.screen

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import viacheslav.chugunov.core.model.Paging
import viacheslav.chugunov.core.repository.GifsNetworkRepository
import viacheslav.chugunov.core.util.AsyncResource
import viacheslav.chugunov.core.util.CoroutineDispatchers
import viacheslav.chugunov.core.util.base.BaseViewModel

class GifsListViewModel(
    private val gifsNetworkRepository: GifsNetworkRepository,
    private val coroutineDispatchers: CoroutineDispatchers
) : BaseViewModel<GifsListState, GifsListAction>(GifsListState()) {
    private var isGifsLoading: Boolean = false
    private var gifsPaging: Paging = Paging.EMPTY
    private val loadGifsMutex = Mutex()

    init {
        loadGifs()
    }

    override fun handleAction(action: GifsListAction) {
        when (action) {
            GifsListAction.RetryToLoadGifs -> {
                state = state.copy(asyncGifs = AsyncResource.Loading())
                loadGifs()
            }
            is GifsListAction.RequestNewGifs -> {
                val shownGifs = state.asyncGifs.dataOrNull ?: emptyList()
                if (action.lastVisibleIndex >= shownGifs.size - 1) {
                    state = state.copy(activeGifsPaging = !isGifsLoading)
                    loadGifs()
                }
            }
        }
    }

    private fun loadGifs() {
        viewModelScope.launch(coroutineDispatchers.io) {
            loadGifsMutex.withLock {
                if (isGifsLoading) return@launch
                isGifsLoading = true
                val offset = gifsPaging.got
                if (offset >= 500) return@launch
                val asyncPagingGifs = gifsNetworkRepository.trending(limit = 50,  offset = offset)
                when (asyncPagingGifs) {
                    is AsyncResource.Failure -> {
                        val error = asyncPagingGifs.error
                        state = state.copy(
                            asyncGifs = AsyncResource.Failure(error),
                            activeGifsPaging = false
                        )
                    }
                    is AsyncResource.Success -> {
                        val oldGifs = state.asyncGifs.dataOrNull ?: emptySet()
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