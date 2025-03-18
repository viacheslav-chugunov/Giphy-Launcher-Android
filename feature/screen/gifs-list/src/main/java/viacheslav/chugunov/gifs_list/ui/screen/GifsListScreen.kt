package viacheslav.chugunov.gifs_list.ui.screen

import android.net.http.HttpException
import android.widget.ImageView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide
import viacheslav.chugunov.core.R
import viacheslav.chugunov.core.model.Gif
import viacheslav.chugunov.core.ui.component.FailureComponent
import viacheslav.chugunov.core.ui.component.LoadingComponent
import viacheslav.chugunov.core.ui.component.TopAppBarComponent
import viacheslav.chugunov.core.util.AsyncResource
import viacheslav.chugunov.core.util.NetworkException
import viacheslav.chugunov.gifs_list.ui.component.GifImageComponent

@Composable
fun GifsListScreen(
    state: GifsListState,
    handle: (GifsListAction) -> Unit,
    openDetailsScreen: (Gif) -> Unit,
    openSearchScreen: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBarComponent(
            title = stringResource(R.string.feed),
            actions = {
                IconButton(
                    onClick = openSearchScreen
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = stringResource(R.string.search)
                    )
                }
            },
            showProgress = state.activeGifsPaging
        )
        when (val asyncGifs = state.asyncGifs) {
            is AsyncResource.Failure -> {
                val messageRes = when (asyncGifs.networkException.errorCause) {
                    NetworkException.Cause.ServiceUnavailable -> R.string.service_unavailable_error_message
                    else -> R.string.no_internet_error_message
                }
                FailureComponent(
                    message = stringResource(messageRes),
                    buttonText = stringResource(R.string.retry),
                    onRetry = {
                        handle(GifsListAction.RetryToLoadGifs)
                    }
                )
            }
            is AsyncResource.Loading -> {
                LoadingComponent(
                    message = stringResource(R.string.uploading_gifs)
                )
            }
            is AsyncResource.Success -> {
                val gifs = asyncGifs.data
                val gridState = rememberLazyStaggeredGridState()
                val canScrollForward = gridState.canScrollForward

                LaunchedEffect(canScrollForward) {
                    if (!canScrollForward && gifs.isNotEmpty()) {
                        handle(GifsListAction.RequestNewGifs(gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0))
                    }
                }

                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Adaptive(150.dp),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                    verticalItemSpacing = 6.dp,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    state = gridState
                ) {
                    items(items = gifs) { gif ->
                        GifImageComponent(
                            url = gif.previewUrl,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(MaterialTheme.shapes.small)
                                .clickable { openDetailsScreen(gif) }
                        )
                    }
                }
            }
        }
    }
}