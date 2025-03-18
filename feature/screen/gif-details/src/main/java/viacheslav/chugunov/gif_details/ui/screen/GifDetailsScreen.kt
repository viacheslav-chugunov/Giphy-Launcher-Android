package viacheslav.chugunov.gif_details.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
            onNavigationBack = navigateBack
        )
        if (state.fullscreenGif) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopEnd
            ) {
                GifImageComponent(
                    url = state.gif.fullUrl,
                    modifier = Modifier.fillMaxSize()
                )
                SmallFloatingActionButton(
                    onClick = { handle(GifDetailsAction.SetGifFullscreen(false)) },
                    modifier = Modifier.padding(12.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close_fullscreen),
                        contentDescription = stringResource(id = R.string.close_fullscreen)
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    GifImageComponent(
                        url = state.gif.fullUrl,
                        modifier = Modifier.fillMaxSize()
                    )
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
                DescriptionFieldComponent(
                    label = stringResource(R.string.title),
                    value = state.gif.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, top = 24.dp, bottom = 16.dp)
                )
                DescriptionFieldComponent(
                    label = stringResource(R.string.username),
                    value = state.gif.username,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, bottom = 16.dp)
                )
                DescriptionFieldComponent(
                    label = stringResource(R.string.uploaded_at),
                    value = state.gif.createdAt,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, bottom = 16.dp)
                )
            }
        }
    }
}