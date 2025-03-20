package viacheslav.chugunov.search_gifs.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import viacheslav.chugunov.core.R
import viacheslav.chugunov.core.model.Gif
import viacheslav.chugunov.core.ui.component.FailureComponent
import viacheslav.chugunov.core.ui.component.GifImageComponent
import viacheslav.chugunov.core.util.AsyncResource
import viacheslav.chugunov.core.util.NetworkException
import viacheslav.chugunov.search_gifs.ui.component.SearchableTopAppBarComponent

@Composable
fun SearchGifsScreen(
    state: SearchGifsState,
    handle: (SearchGifsAction) -> Unit,
    openDetailsScreen: (Gif) -> Unit,
    navigateBack: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current

    LaunchedEffect(focusRequester) {
        focusRequester.requestFocus()
        delay(100)
        keyboard?.show()
    }

    Column {
        SearchableTopAppBarComponent(
            query = state.query,
            onQueryChange = { query ->
                handle(SearchGifsAction.Search(query))
            },
            onNavigationBack = navigateBack,
            showProgress = state.queryProcessing && state.query.isNotBlank() || state.activeGifsPaging,
            focusRequester = focusRequester
        )
        when (val asyncGifs = state.asyncGifs) {
            is AsyncResource.Failure -> {
                val messageRes: Int = when (asyncGifs.networkException.errorCause) {
                    NetworkException.Cause.ServiceUnavailable -> {
                        R.string.service_unavailable_error_message
                    }
                    else -> {
                        R.string.no_internet_error_message
                    }
                }
                FailureComponent(
                    message = stringResource(messageRes),
                    onRetry = {
                        handle(SearchGifsAction.RetrySearch)
                    }
                )
            }
            is AsyncResource.Loading -> {

            }
            is AsyncResource.Success -> {
                val gifs = asyncGifs.data
                if (gifs.isEmpty() && (!state.queryProcessing || state.query.isBlank())) {
                    val messageRes = if (state.query.isBlank()) {
                        R.string.empty_gif_search_request
                    } else {
                        R.string.gifs_not_found
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(Modifier.weight(1f))
                        Text(
                            text = stringResource(id = messageRes),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.weight(1.75f))
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(150.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(8.dp),
                    ) {
                        items(asyncGifs.data, key = { it.id }) { gif ->
                            GifImageComponent(
                                url = gif.previewUrl,
                                contentDescription = gif.title,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f)
                                    .clip(MaterialTheme.shapes.small)
                                    .clickable { openDetailsScreen(gif) }
                            )
                        }
                    }
                }
            }
        }
    }
}