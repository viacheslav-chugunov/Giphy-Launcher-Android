package viacheslav.chugunov.giphy_launcher

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import viacheslav.chugunov.core.ui.theme.GiphyLauncherTheme
import viacheslav.chugunov.giphy_launcher.ui.screen.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enable chucker notifications
        if (BuildConfig.DEBUG && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permissions = arrayOf(android.Manifest.permission.POST_NOTIFICATIONS)
            requestPermissions(permissions, 1)
        }
        enableEdgeToEdge()
        setContent {
            GiphyLauncherTheme {
                MainScreen()
            }
        }
    }
}