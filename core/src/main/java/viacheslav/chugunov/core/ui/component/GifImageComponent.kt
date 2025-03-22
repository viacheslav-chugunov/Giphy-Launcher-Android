package viacheslav.chugunov.core.ui.component

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.skydoves.landscapist.rememberDrawablePainter
import viacheslav.chugunov.core.ui.theme.GiphyLauncherTheme

@Composable
fun GifImageComponent(
    url: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit
) {
    val context = LocalContext.current
    val glide = remember { Glide.with(context) }
    var drawable: Drawable? by remember { mutableStateOf(null) }
    val target = remember {
        object : CustomTarget<GifDrawable>() {
            override fun onResourceReady(
                resource: GifDrawable,
                transition: Transition<in GifDrawable>?
            ) {
                drawable = resource
            }
            override fun onLoadCleared(placeholder: Drawable?) {
                drawable = null
            }
        }
    }

    DisposableEffect(Unit) {
        glide
            .asGif()
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .load(url)
            .into(target)
        onDispose {
            glide.clear(target)
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        if (drawable != null) {
            Image(
                painter = rememberDrawablePainter(drawable),
                contentDescription = contentDescription,
                contentScale = contentScale,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            CircularProgressIndicator()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    GiphyLauncherTheme {
        GifImageComponent(
            url = "https://cdn.shortpixel.ai/spai/q_lossy+w_1345+to_webp+ret_img/mailfloss.com/storage/2019/08/5d667803a7a67_gif1-Everyonelovesgifs_4ef1dbef8a604a3e1b26eebf2c000ef0.gif",
            contentDescription = "contentDescription"
        )
    }
}