package viacheslav.chugunov.giphy_launcher.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import viacheslav.chugunov.gifs_list.ui.screen.GifsListScreen
import viacheslav.chugunov.gifs_list.ui.screen.GifsListViewModel
import viacheslav.chugunov.giphy_launcher.NavDestinations
import viacheslav.chugunov.giphy_launcher.extensions.singleNavigate

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavDestinations.GifsList) {
        composable<NavDestinations.GifsList> {
            val viewModel = koinViewModel<GifsListViewModel>()
            GifsListScreen(
                state = viewModel.stateFlow.collectAsState().value,
                handle = viewModel::handleAction,
                openDetailsScreen = {
                    navController.singleNavigate(NavDestinations.GifDetails)
                },
                openSearchScreen = {
                    navController.singleNavigate(NavDestinations.SearchGifs)
                }
            )
        }
        composable<NavDestinations.GifDetails> {

        }
        composable<NavDestinations.SearchGifs> {

        }
    }
}