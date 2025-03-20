package viacheslav.chugunov.gif_details.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import viacheslav.chugunov.core.R
import viacheslav.chugunov.core.ui.component.GifImageComponent
import viacheslav.chugunov.core.ui.component.TopAppBarComponent
import viacheslav.chugunov.gif_details.ui.component.DescriptionFieldComponent

@Composable
fun GifDetailsScreen(
    state: GifDetailsState,
    handle: (GifDetailsAction) -> Unit,
    navigateBack: () -> Unit
) {
    Column {
        TopAppBarComponent(
            title = stringResource(id = R.string.details),
            onNavigationBack = navigateBack,
            actions = {
                if (state.fullscreenGif) {
                    SmallFloatingActionButton(
                        onClick = { handle(GifDetailsAction.SetGifFullscreen(false)) },
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_close_fullscreen),
                            contentDescription = stringResource(id = R.string.close_fullscreen)
                        )
                    }
                } else {
                    SmallFloatingActionButton(
                        onClick = { handle(GifDetailsAction.SetGifFullscreen(true)) },
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_open_fullscreen),
                            contentDescription = stringResource(id = R.string.open_fullscreen)
                        )
                    }
                }
            }
        )
        if (state.fullscreenGif) {
            GifImageComponent(
                url = state.gif.fullUrl,
                contentDescription = state.gif.title,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                GifImageComponent(
                    url = state.gif.fullUrl,
                    contentDescription = state.gif.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(2f)
                )
                DescriptionFieldComponent(
                    label = stringResource(R.string.title),
                    value = state.gif.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp, top = 24.dp, bottom = 16.dp)
                )
                DescriptionFieldComponent(
                    label = stringResource(R.string.username),
                    value = state.gif.username,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp, bottom = 16.dp)
                )
                DescriptionFieldComponent(
                    label = stringResource(R.string.uploaded_at),
                    value = state.gif.createdAt,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp, bottom = 16.dp)
                )
            }
        }
    }
}