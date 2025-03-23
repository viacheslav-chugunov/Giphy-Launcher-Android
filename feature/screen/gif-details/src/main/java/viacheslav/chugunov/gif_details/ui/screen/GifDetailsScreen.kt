package viacheslav.chugunov.gif_details.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import viacheslav.chugunov.core.R
import viacheslav.chugunov.core.extensions.isLandscape
import viacheslav.chugunov.core.model.Gif
import viacheslav.chugunov.core.ui.component.GifImageComponent
import viacheslav.chugunov.core.ui.component.TopAppBarComponent
import viacheslav.chugunov.core.ui.theme.GiphyLauncherTheme
import viacheslav.chugunov.gif_details.ui.component.DescriptionFieldComponent
import viacheslav.chugunov.gif_details.ui.component.GifDescriptionComponent

@Composable
fun GifDetailsScreen(
    state: GifDetailsState,
    handle: (GifDetailsAction) -> Unit,
    navigateBack: () -> Unit
) {
    val configuration = LocalConfiguration.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (!configuration.isLandscape || !state.fullscreenGif) {
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
        }
        if (configuration.isLandscape) {
            if (state.fullscreenGif) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    GifImageComponent(
                        url = state.gif.fullUrl,
                        contentDescription = state.gif.title,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.TopStart,
                    ) {
                        IconButton(
                            onClick = navigateBack,
                            modifier = Modifier.padding(start = 12.dp, top = 24.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_arrow_back),
                                contentDescription = stringResource(id = R.string.close_fullscreen)
                            )
                        }
                    }
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.TopEnd,
                    ) {
                        SmallFloatingActionButton(
                            onClick = { handle(GifDetailsAction.SetGifFullscreen(false)) },
                            modifier = Modifier.padding(end = 12.dp, top = 24.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_close_fullscreen),
                                contentDescription = stringResource(id = R.string.close_fullscreen)
                            )
                        }
                    }
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        GifImageComponent(
                            url = state.gif.fullUrl,
                            contentDescription = state.gif.title,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .verticalScroll(rememberScrollState())
                    ) {
                        GifDescriptionComponent(
                            title = state.gif.title,
                            username = state.gif.username,
                            createdAt = state.gif.createdAt
                        )
                    }
                }
            }
        } else {
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
                    GifDescriptionComponent(
                        title = state.gif.title,
                        username = state.gif.username,
                        createdAt = state.gif.createdAt
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    GiphyLauncherTheme {
        GifDetailsScreen(
            state = GifDetailsState(
                Gif(
                    id = "id",
                    title = "title",
                    username = "username",
                    createdAt = "createdAt",
                    previewUrl = "https://cdn.shortpixel.ai/spai/q_lossy+w_1345+to_webp+ret_img/mailfloss.com/storage/2019/08/5d667803a7a67_gif1-Everyonelovesgifs_4ef1dbef8a604a3e1b26eebf2c000ef0.gif",
                    fullUrl = "https://cdn.shortpixel.ai/spai/q_lossy+w_1345+to_webp+ret_img/mailfloss.com/storage/2019/08/5d667803a7a67_gif1-Everyonelovesgifs_4ef1dbef8a604a3e1b26eebf2c000ef0.gif"
                )
            ),
            handle = {},
            navigateBack = {}
        )
    }
}