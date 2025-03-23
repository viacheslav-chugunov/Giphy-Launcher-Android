package viacheslav.chugunov.giphy_launcher.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test
import viacheslav.chugunov.core.util.AsyncResource
import viacheslav.chugunov.gifs_list.ui.screen.GifsListScreen
import viacheslav.chugunov.gifs_list.ui.screen.GifsListState
import viacheslav.chugunov.giphy_launcher.mock.MockGif

class GifsListScreenTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun show_loading_if_gifs_are_not_loaded() {
        rule.setContent {
            GifsListScreen(
                state = GifsListState(
                    asyncGifs = AsyncResource.Loading(),
                    activeGifsPaging = true
                )
            )
        }
        rule.onNodeWithTag("LoadingComponent").assertIsDisplayed()
        rule.onNodeWithTag("TopAppBarComponent.LinearProgressIndicator").assertIsDisplayed()

    }

    @Test
    fun show_loading_if_there_are_loaded_gifs() {
        rule.setContent {
            GifsListScreen(
                state = GifsListState(
                    asyncGifs = AsyncResource.Success(listOf(MockGif(1), MockGif(2))),
                    activeGifsPaging = true
                )
            )
        }
        rule.onNodeWithTag("LoadingComponent").assertIsNotDisplayed()
        rule.onNodeWithTag("TopAppBarComponent.LinearProgressIndicator").assertIsDisplayed()
    }

    @Test
    fun show_error_if_gifs_failed_to_load() {
        rule.setContent {
            GifsListScreen(
                state = GifsListState(
                    asyncGifs = AsyncResource.Failure(Exception()),
                    activeGifsPaging = false
                )
            )
        }
        rule.onNodeWithTag("FailureComponent").assertIsDisplayed()
    }

    @Test
    fun show_gifs_if_they_were_successfully_loaded() {
        rule.setContent {
            GifsListScreen(
                state = GifsListState(
                    asyncGifs = AsyncResource.Success(listOf(MockGif(1), MockGif(2))),
                    activeGifsPaging = false
                )
            )
        }
        rule.onNodeWithTag("GifsGridComponent").assertIsDisplayed()
        rule.onNodeWithTag("TopAppBarComponent.LinearProgressIndicator").assertIsNotDisplayed()
    }

    @Test
    fun show_loading_and_gifs_while_loading_new_gifs_page() {
        rule.setContent {
            GifsListScreen(
                state = GifsListState(
                    asyncGifs = AsyncResource.Success(listOf(MockGif(1), MockGif(2))),
                    activeGifsPaging = true
                )
            )
        }
        rule.onNodeWithTag("GifsGridComponent").assertIsDisplayed()
        rule.onNodeWithTag("TopAppBarComponent.LinearProgressIndicator").assertIsDisplayed()
    }

    @Composable
    private fun GifsListScreen(
        state: GifsListState
    ) {
        GifsListScreen(
            state = state,
            handle = {},
            openDetailsScreen = {},
            openSearchScreen = {}
        )
    }
}