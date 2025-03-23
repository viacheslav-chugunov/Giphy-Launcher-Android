package viacheslav.chugunov.giphy_launcher.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test
import viacheslav.chugunov.gif_details.ui.screen.GifDetailsScreen
import viacheslav.chugunov.gif_details.ui.screen.GifDetailsState
import viacheslav.chugunov.giphy_launcher.mock.MockGif

class GifDetailsScreenTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun show_description_not_in_full_screen() {
        rule.setContent {
            GifDetailsScreen(
                state = GifDetailsState(
                    gif = MockGif(1),
                    fullscreenGif = false
                )
            )
        }
        rule.onNodeWithTag("GifDescriptionComponent").assertIsDisplayed()
    }

    @Test
    fun hide_description_in_full_screen() {
        rule.setContent {
            GifDetailsScreen(
                state = GifDetailsState(
                    gif = MockGif(1),
                    fullscreenGif = true
                )
            )
        }
        rule.onNodeWithTag("GifDescriptionComponent").assertIsNotDisplayed()
    }

    @Composable
    private fun GifDetailsScreen(state: GifDetailsState) {
        GifDetailsScreen(
            state = state,
            handle = {},
            navigateBack = {}
        )
    }
}