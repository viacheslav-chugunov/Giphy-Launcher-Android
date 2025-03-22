package viacheslav.chugunov.core.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import viacheslav.chugunov.core.extensions.isLandscape
import viacheslav.chugunov.core.model.Gif

@Composable
fun GifsGridComponent(
    gifs: List<Gif>,
    onGifClick: (Gif) -> Unit,
    onRequestNewGifs: () -> Unit
) {
    val gridState = rememberLazyGridState()
    val configuration = LocalConfiguration.current
    val canScrollForward = gridState.canScrollForward

    LaunchedEffect(canScrollForward) {
        val lastVisibleIndex = gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
        if (!canScrollForward && gifs.isNotEmpty() && lastVisibleIndex >= gifs.size - 1) {
            onRequestNewGifs()
        }
    }

    if (configuration.isLandscape) {
        LazyHorizontalGrid(
            rows = GridCells.Adaptive(80.dp),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            state = gridState
        ) {
            items(items = gifs, key = { it.id }) { gif ->
                GifImageComponent(
                    url = gif.previewUrl,
                    contentDescription = gif.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(MaterialTheme.shapes.small)
                        .clickable { onGifClick(gif) }
                )
            }
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(150.dp),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            state = gridState
        ) {
            items(items = gifs, key = { it.id }) { gif ->
                GifImageComponent(
                    url = gif.previewUrl,
                    contentDescription = gif.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(MaterialTheme.shapes.small)
                        .clickable { onGifClick(gif) }
                )
            }
        }
    }
}