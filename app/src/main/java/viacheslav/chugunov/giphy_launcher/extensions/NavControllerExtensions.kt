package viacheslav.chugunov.giphy_launcher.extensions

import androidx.navigation.NavController

private const val NAVIGATION_DELAY = 1500
private var lastNavigationBack = 0L
private var lastNavigation = 0L

fun NavController.singlePopBackStack() {
    val now = System.currentTimeMillis()
    if (now - lastNavigationBack > NAVIGATION_DELAY) {
        lastNavigationBack = now
        popBackStack()
    }
}

fun NavController.singleNavigate(destination: Any) {
    val now = System.currentTimeMillis()
    if (now - lastNavigation > NAVIGATION_DELAY) {
        lastNavigation = now
        navigate(destination)
    }
}