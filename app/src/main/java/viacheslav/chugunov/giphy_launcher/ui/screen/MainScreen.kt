package viacheslav.chugunov.giphy_launcher.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import viacheslav.chugunov.core.model.Gif
import viacheslav.chugunov.gif_details.ui.screen.GifDetailsScreen
import viacheslav.chugunov.gif_details.ui.screen.GifDetailsViewModel
import viacheslav.chugunov.gifs_list.ui.screen.GifsListScreen
import viacheslav.chugunov.gifs_list.ui.screen.GifsListViewModel
import viacheslav.chugunov.giphy_launcher.NavDestinations
import viacheslav.chugunov.giphy_launcher.extensions.navArgument
import viacheslav.chugunov.giphy_launcher.extensions.singleNavigate
import viacheslav.chugunov.giphy_launcher.extensions.singlePopBackStack

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavDestinations.GifsList) {
        composable<NavDestinations.GifsList> {
            val viewModel = koinViewModel<GifsListViewModel>()
            GifsListScreen(
                state = viewModel.stateValue,
                handle = viewModel::handleAction,
                openDetailsScreen = { gif ->
                    navController.singleNavigate(NavDestinations.GifDetails(gif))
                },
                openSearchScreen = {
                    navController.singleNavigate(NavDestinations.SearchGifs)
                }
            )
        }
        composable<NavDestinations.GifDetails>(
            typeMap = mapOf(navArgument<Gif>())
        ) {
            val gif = it.toRoute<NavDestinations.GifDetails>().gif
            val viewModel = koinViewModel<GifDetailsViewModel>(
                parameters = { parametersOf(gif) }
            )
            GifDetailsScreen(
                state = viewModel.stateValue,
                handle = viewModel::handleAction,
                navigateBack = { navController.singlePopBackStack() }
            )
        }
        composable<NavDestinations.SearchGifs> {

        }
    }
}