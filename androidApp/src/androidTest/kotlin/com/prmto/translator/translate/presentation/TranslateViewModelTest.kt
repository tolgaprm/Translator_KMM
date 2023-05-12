package com.prmto.translator.translate.presentation

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.prmto.translator.android.R
import com.prmto.translator.android.core.util.TestTags
import com.prmto.translator.android.translate.presentation.TranslateScreen
import com.prmto.translator.core.presentation.UiLanguage
import com.prmto.translator.translate.data.local.FakeHistoryDataSource
import com.prmto.translator.translate.data.remote.FakeTranslateClient
import com.prmto.translator.translate.domain.translate.TranslateUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class TranslateViewModelTest {

    @get:Rule
    val composeRule = createComposeRule()

    private lateinit var viewModel: TranslateViewModel
    private lateinit var client: FakeTranslateClient
    private lateinit var dataSource: FakeHistoryDataSource
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        client = FakeTranslateClient()
        dataSource = FakeHistoryDataSource()
        val translateUseCase = TranslateUseCase(
            client = client,
            historyDataSource = dataSource
        )
        viewModel = TranslateViewModel(
            translateUseCase = translateUseCase,
            historyDataSource = dataSource,
            coroutineScope = CoroutineScope(Dispatchers.Main)
        )
        composeRule.setContent {
            val state = viewModel.state.collectAsState().value
            TranslateScreen(
                state = state,
                onEvent = viewModel::onEvent
            )
        }
    }

    @Test
    fun changeTranslationTextStateProperlyUpdated() = runBlocking<Unit> {
        val inputText = "This text is for translation"
        composeRule.onNodeWithTag(TestTags.translateTextField).performTextInput(inputText)
        viewModel.state.test {
            val state = awaitItem()
            assertThat(state.fromText).isEqualTo(inputText)
        }
    }

    @Test
    fun chooseFromLanguage_StateUpdateProperly() = runBlocking<Unit> {
        composeRule.onNodeWithContentDescription(TestTags.fromDropdown).performClick()
        viewModel.state.test {
            var state = awaitItem()
            assertThat(state.isChoosingFromLanguage).isTrue()

            composeRule.onNodeWithTag("${TestTags.languageDropDownItem}en").performClick()
            state = awaitItem()
            assertThat(state.isChoosingFromLanguage).isFalse()
            assertThat(state.fromLanguage).isEqualTo(UiLanguage.byCode("en"))
        }

    }

    @Test
    fun chooseToLanguage_StateUpdateProperly() = runBlocking<Unit> {
        composeRule.onNodeWithContentDescription(TestTags.toDropdown).performClick()
        viewModel.state.test {
            var state = awaitItem()
            assertThat(state.isChoosingToLanguage).isTrue()

            composeRule.onNodeWithTag("${TestTags.languageDropDownItem}de").performClick()
            state = awaitItem()
            assertThat(state.isChoosingToLanguage).isFalse()
            assertThat(state.toLanguage).isEqualTo(UiLanguage.byCode("de"))
        }
    }

    @Test
    fun clickSwapLanguageButton_StateUpdatedProperly() = runBlocking<Unit> {
        chooseFromDropDownLanguage("en")
        chooseToDropDownLanguage("de")
        composeRule.onNodeWithContentDescription(
            context.getString(R.string.swap_languages),
            useUnmergedTree = true
        ).performClick()
        viewModel.state.test {
            val state = awaitItem()
            assertThat(state.fromLanguage).isEqualTo(UiLanguage.byCode("de"))
            assertThat(state.toLanguage).isEqualTo(UiLanguage.byCode("en"))
        }
    }

    private fun chooseFromDropDownLanguage(languageCode: String) {
        composeRule.onNodeWithContentDescription(TestTags.fromDropdown).performClick()
        composeRule.onNodeWithTag("${TestTags.languageDropDownItem}$languageCode").performClick()
    }

    private fun chooseToDropDownLanguage(languageCode: String) {
        composeRule.onNodeWithContentDescription(TestTags.toDropdown).performClick()
        composeRule.onNodeWithTag("${TestTags.languageDropDownItem}$languageCode").performClick()
    }

}