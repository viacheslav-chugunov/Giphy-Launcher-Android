package viacheslav.chugunov.core.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import viacheslav.chugunov.core.R
import viacheslav.chugunov.core.ui.theme.GiphyLauncherTheme

@Composable
fun FailureComponent(
    message: String,
    onRetry: (() -> Unit)? = null,
) {
    Column(
        modifier = Modifier
            .testTag("FailureComponent")
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(Modifier.weight(1f))
        Text(
            text = message,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = { onRetry?.invoke() }
        ) {
            Text(
                text = stringResource(R.string.retry),
                style = MaterialTheme.typography.labelLarge
            )
        }
        Spacer(Modifier.weight(1.75f))
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    GiphyLauncherTheme {
        FailureComponent(
            message = "message",
            onRetry = {}
        )
    }
}