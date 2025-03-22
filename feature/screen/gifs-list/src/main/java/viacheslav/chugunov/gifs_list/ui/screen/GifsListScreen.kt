package viacheslav.chugunov.gifs_list.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import viacheslav.chugunov.core.R
import viacheslav.chugunov.core.model.Gif
import viacheslav.chugunov.core.ui.component.FailureComponent
import viacheslav.chugunov.core.ui.component.GifsGridComponent
import viacheslav.chugunov.core.ui.component.LoadingComponent
import viacheslav.chugunov.core.ui.component.TopAppBarComponent
import viacheslav.chugunov.core.ui.theme.GiphyLauncherTheme
import viacheslav.chugunov.core.util.AsyncResource
import viacheslav.chugunov.core.util.NetworkException

@Composable
fun GifsListScreen(
    state: GifsListState,
    handle: (GifsListAction) -> Unit,
    openDetailsScreen: (Gif) -> Unit,
    openSearchScreen: () -> Unit,
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
                    onRetry = {
                        handle(GifsListAction.RetryToLoadGifs)
                    }
                )
            }
            is AsyncResource.Loading -> {
                LoadingComponent(
                    message = stringResource(R.string.loading_gifs)
                )
            }
            is AsyncResource.Success -> {
                GifsGridComponent(
                    gifs = asyncGifs.data,
                    onGifClick = openDetailsScreen,
                    onRequestNewGifs = {
                        handle(GifsListAction.RequestNewGifs)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    GiphyLauncherTheme {
        GifsListScreen(
            state = GifsListState(),
            handle = {},
            openDetailsScreen = {},
            openSearchScreen = {}
        )
    }
}