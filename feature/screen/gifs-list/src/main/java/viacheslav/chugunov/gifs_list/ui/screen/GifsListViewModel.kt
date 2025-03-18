package viacheslav.chugunov.gifs_list.ui.screen

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import viacheslav.chugunov.core.model.Paging
import viacheslav.chugunov.core.repository.GifsNetworkRepository
import viacheslav.chugunov.core.util.AsyncResource
import viacheslav.chugunov.core.util.base.BaseViewModel

class GifsListViewModel(
    private val gifsNetworkRepository: GifsNetworkRepository
) : BaseViewModel<GifsListState, GifsListAction>(GifsListState()) {
    private var isGifsLoading: Boolean = false
    private var gifsPaging: Paging = Paging.EMPTY

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
                if (!gifsPaging.isEmpty && action.lastVisibleIndex >= gifsPaging.got - 1) {
                    state = state.copy(activeGifsPaging = !isGifsLoading)
                    loadGifs()
                }
            }
        }
    }

    private fun loadGifs() {
        if (!gifsPaging.isEmpty && gifsPaging.got >= gifsPaging.total) return
        if (isGifsLoading) return
        isGifsLoading = true
        viewModelScope.launch {
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
                    state = state.copy(
                        asyncGifs = AsyncResource.Success(oldGifs + newGifs),
                        activeGifsPaging = false
                    )
                }
                else -> {}
            }
            isGifsLoading = false
        }
    }
}