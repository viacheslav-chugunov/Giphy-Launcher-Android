package viacheslav.chugunov.giphy_launcher

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import viacheslav.chugunov.core.ui.theme.GiphyLauncherTheme
import viacheslav.chugunov.giphy_launcher.ui.screen.MainScreen

class MainActivity : ComponentActivity() {
    private var showSplash: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyChuckerNotifications()
        showSplash = savedInstanceState?.getBoolean("showSplash") ?: showSplash
        installSplashScreen().setKeepOnScreenCondition { showSplash }
        enableEdgeToEdge()
        setContent {
            GiphyLauncherTheme {
                MainScreen(
                    hideSplash = {
                        showSplash = false
                    }
                )
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("showSplash", showSplash)
    }

    private fun applyChuckerNotifications() {
        if (BuildConfig.DEBUG && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permissions = arrayOf(android.Manifest.permission.POST_NOTIFICATIONS)
            requestPermissions(permissions, 1)
        }
    }
}