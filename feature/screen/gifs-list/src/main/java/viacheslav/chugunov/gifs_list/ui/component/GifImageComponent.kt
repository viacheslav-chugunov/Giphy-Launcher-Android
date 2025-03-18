package viacheslav.chugunov.gifs_list.ui.component

import android.widget.ImageView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide

@Composable
fun GifImageComponent(
    url: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val view = remember { ImageView(context) }

    DisposableEffect(context) {
        Glide.with(context)
            .asGif()
            .load(url)
            .into(view)
        onDispose {
            Glide.with(context).clear(view)
        }
    }

    AndroidView(
        factory = { view },
        modifier = modifier
    )
}