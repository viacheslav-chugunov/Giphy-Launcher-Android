package viacheslav.chugunov.gif_details.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import viacheslav.chugunov.core.R

@Composable
internal fun GifDescriptionComponent(
    title: String,
    username: String,
    createdAt: String
) {
    Column(
        modifier = Modifier.testTag("GifDescriptionComponent")
    ) {
        DescriptionFieldComponent(
            label = stringResource(R.string.title),
            value = title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp, top = 24.dp, bottom = 16.dp)
        )
        DescriptionFieldComponent(
            label = stringResource(R.string.username),
            value = username,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp, bottom = 16.dp)
        )
        DescriptionFieldComponent(
            label = stringResource(R.string.uploaded_at),
            value = createdAt,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp, bottom = 16.dp)
        )
    }
}