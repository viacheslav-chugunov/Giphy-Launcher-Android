package viacheslav.chugunov.giphy_launcher.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test
import viacheslav.chugunov.core.util.AsyncResource
import viacheslav.chugunov.giphy_launcher.mock.MockGif
import viacheslav.chugunov.search_gifs.ui.screen.SearchGifsScreen
import viacheslav.chugunov.search_gifs.ui.screen.SearchGifsState

class SearchGifsScreenTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun show_message_if_request_is_blank() {
        rule.setContent {
            SearchGifsScreen(
                state = SearchGifsState(
                    query = "   ",
                )
            )
        }
        rule.onNodeWithTag("MessageComponent").assertIsDisplayed()
    }

    @Test
    fun show_loading_if_there_is_a_request_and_no_gifs_shown() {
        rule.setContent {
            SearchGifsScreen(
                state = SearchGifsState(
                    query = "query",
                    queryProcessing = false,
                    activeGifsPaging = true,
                    asyncGifs = AsyncResource.Success(emptyList())
                )
            )
        }
        rule.onNodeWithTag("LoadingComponent").assertIsDisplayed()
        rule.onNodeWithTag("SearchableTopAppBarComponent.LinearProgressIndicator").assertIsDisplayed()
    }

    @Test
    fun show_loading_if_there_is_a_request_and_there_are_gifs_shown() {
        rule.setContent {
            SearchGifsScreen(
                state = SearchGifsState(
                    query = "query",
                    queryProcessing = false,
                    activeGifsPaging = true,
                    asyncGifs = AsyncResource.Success(listOf(MockGif(1), MockGif(2)))
                )
            )
        }
        rule.onNodeWithTag("LoadingComponent").assertIsNotDisplayed()
        rule.onNodeWithTag("SearchableTopAppBarComponent.LinearProgressIndicator").assertIsDisplayed()
    }

    @Test
    fun show_error_if_gifs_failed_to_load() {
        rule.setContent {
            SearchGifsScreen(
                state = SearchGifsState(
                    query = "query",
                    queryProcessing = false,
                    activeGifsPaging = true,
                    asyncGifs = AsyncResource.Failure(Exception())
                )
            )
        }
        rule.onNodeWithTag("FailureComponent").assertIsDisplayed()
    }

    @Test
    fun show_gifs_if_they_were_successfully_loaded() {
        rule.setContent {
            SearchGifsScreen(
                state = SearchGifsState(
                    query = "query",
                    queryProcessing = false,
                    activeGifsPaging = false,
                    asyncGifs = AsyncResource.Success(listOf(MockGif(1), MockGif(2)))
                )
            )
        }
        rule.onNodeWithTag("SearchableTopAppBarComponent.LinearProgressIndicator").assertIsNotDisplayed()
        rule.onNodeWithTag("GifsGridComponent").assertIsDisplayed()
    }

    @Test
    fun show_gifs_if_they_were_successful_and_a_new_page_of_gifs_started_loading() {
        rule.setContent {
            SearchGifsScreen(
                state = SearchGifsState(
                    query = "query",
                    queryProcessing = false,
                    activeGifsPaging = true,
                    asyncGifs = AsyncResource.Success(listOf(MockGif(1), MockGif(2)))
                )
            )
        }
        rule.onNodeWithTag("SearchableTopAppBarComponent.LinearProgressIndicator").assertIsDisplayed()
        rule.onNodeWithTag("GifsGridComponent").assertIsDisplayed()
    }

    @Composable
    private fun SearchGifsScreen(state: SearchGifsState) {
        SearchGifsScreen(
            state = state,
            handle = {},
            openDetailsScreen = {},
            navigateBack = {}
        )
    }

}