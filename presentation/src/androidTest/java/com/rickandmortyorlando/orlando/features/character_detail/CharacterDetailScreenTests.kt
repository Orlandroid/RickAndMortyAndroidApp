package com.rickandmortyorlando.orlando.features.character_detail

import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.isNotDisplayed
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
        composeTestRule.onNodeWithTag("CharacterDetailSkeleton").isDisplayed()
    }

    @Test
    fun verify_content_state_with_location_characterDetail_characterOfThisLocation() {
        setScreen(
            state = BaseViewState.Content(
                result = CharacterDetailUiState(
                    location = Location.mockLocation(),
                    characterDetail = Character.mockCharacter(),
                    characterOfThisLocation = listOf(Character.mockCharacter())
                )
            )
        )
        composeTestRule.onNodeWithTag("CharacterDetailScreenContent").isDisplayed()
        composeTestRule.onNodeWithTag("LocationDetails").isDisplayed()
        composeTestRule.onNodeWithTag("CharacterDetail").isDisplayed()
        composeTestRule.onNodeWithTag("CharactersOfThisLocation").isDisplayed()
    }

    @Test
    fun verify_content_state_with_characterDetail_characterOfThisLocation() {
        setScreen(
            state = BaseViewState.Content(
                result = CharacterDetailUiState(
                    characterDetail = Character.mockCharacter(),
                    characterOfThisLocation = listOf(Character.mockCharacter())
                )
            )
        )
        composeTestRule.onNodeWithTag("CharacterDetailScreenContent").isDisplayed()
        composeTestRule.onNodeWithTag("LocationDetails").isNotDisplayed()
        composeTestRule.onNodeWithTag("CharacterDetail").isDisplayed()
        composeTestRule.onNodeWithTag("CharactersOfThisLocation").isDisplayed()
    }

    @Test
    fun verify_content_state_with_characterOfThisLocation() {
        setScreen(
            state = BaseViewState.Content(
                result = CharacterDetailUiState(
                    characterOfThisLocation = listOf(Character.mockCharacter())
                )
            )
        )
        composeTestRule.onNodeWithTag("CharacterDetailScreenContent").isNotDisplayed()
        composeTestRule.onNodeWithTag("LocationDetails").isNotDisplayed()
        composeTestRule.onNodeWithTag("CharacterDetail").isNotDisplayed()
        composeTestRule.onNodeWithTag("CharactersOfThisLocation").isDisplayed()
    }

    @Test
    fun verify_error_state() {
        setScreen(state = BaseViewState.Error(message = "error"))
        composeTestRule.onNodeWithTag("ErrorScreen").isDisplayed()
    }
}