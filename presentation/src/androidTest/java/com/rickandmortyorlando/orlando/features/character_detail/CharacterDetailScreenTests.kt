package com.rickandmortyorlando.orlando.features.character_detail

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.domain.models.characters.Character
import com.example.domain.models.location.Location
import com.rickandmortyorlando.orlando.features.characters_detail.CharacterDetailScreen
import com.rickandmortyorlando.orlando.features.characters_detail.CharacterDetailUiState
import com.rickandmortyorlando.orlando.state.BaseViewState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class CharacterDetailScreenTests {


    @get: Rule
    val composeTestRule = createComposeRule()


    private fun setScreen(state: BaseViewState<CharacterDetailUiState>) {
        composeTestRule.setContent {
            CharacterDetailScreen(
                viewState = state,
                clickOnCharacter = {},
                clickOnNumberOfEpisodes = {},
                onBack = {}
            )
        }
    }

    @Test
    fun verify_loading_state() {
        setScreen(state = BaseViewState.Loading)
        composeTestRule.onNodeWithTag("CharacterDetailSkeleton").assertExists()
    }

    @Test
    fun verify_content_state() {
        setScreen(
            state = BaseViewState.Content(
                result = CharacterDetailUiState(
                    location = Location.mockLocation(),
                    characterDetail = Character.mockCharacter(),
                    characterOfThisLocation = listOf(Character.mockCharacter()),
                    idsOfEpisodes = "1,2"
                )
            )
        )
        composeTestRule.onNodeWithTag("CharacterDetailScreenContent").assertExists()
    }

    @Test
    fun verify_error_state() {
        setScreen(state = BaseViewState.Error(message = "error"))
        composeTestRule.onNodeWithTag("ErrorScreen").assertExists()
    }
}