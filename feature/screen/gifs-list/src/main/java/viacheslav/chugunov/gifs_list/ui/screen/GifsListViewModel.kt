package viacheslav.chugunov.gifs_list.ui.screen

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import viacheslav.chugunov.core.model.Paging
import viacheslav.chugunov.core.repository.GifsNetworkRepository
import viacheslav.chugunov.core.util.AsyncResource
import viacheslav.chugunov.core.util.base.BaseViewModel

class GifsListViewModel(
    private val gifsNetworkRepository: GifsNetworkRepository
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
                gifsPaging = Paging.EMPTY
                state = state.copy(asyncGifs = AsyncResource.Loading())
                loadGifs()
            }
            is GifsListAction.RequestNewGifs -> {
                val shownGifs = state.asyncGifs.dataOrNull ?: emptyList()
                if (!gifsPaging.isEmpty && action.lastVisibleIndex >= shownGifs.size - 1) {
                    state = state.copy(activeGifsPaging = !isGifsLoading)
                    loadGifs()
                }
            }
        }
    }

    private fun loadGifs() {
        viewModelScope.launch(Dispatchers.IO) {
            loadGifsMutex.withLock {
                if (isGifsLoading) return@launch
                isGifsLoading = true
                val asyncPagingGifs = gifsNetworkRepository.trending(limit = 50,  offset = gifsPaging.got)
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
                        val actualGifs = (oldGifs + newGifs).toSet().toList()
                        state = state.copy(
                            asyncGifs = AsyncResource.Success(actualGifs),
                            activeGifsPaging = false
                        )
                    }
                    else -> {}
                }
                delay(1500)
                isGifsLoading = false
            }
        }
    }
}