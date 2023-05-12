package com.prmto.translator.presentation

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasTextExactly
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ApplicationProvider
import androidx.test.rule.GrantPermissionRule
import com.prmto.translator.android.MainActivity
import com.prmto.translator.android.R
import com.prmto.translator.android.core.util.TestTags
import com.prmto.translator.android.di.AppModule
import com.prmto.translator.android.voice_to_text.di.VoiceToTextModule
import com.prmto.translator.translate.data.remote.FakeTranslateClient
import com.prmto.translator.translate.domain.translate.TranslateClient
import com.prmto.translator.voice_to_text.domain.VoiceToTextParser
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject


@HiltAndroidTest
@UninstallModules(AppModule::class, VoiceToTextModule::class)
class E2ETests {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val permissionRule = GrantPermissionRule.grant(android.Manifest.permission.RECORD_AUDIO)

    @Inject
    lateinit var fakeVoiceParser: VoiceToTextParser

    @Inject
    lateinit var fakeClient: TranslateClient

    @Before
    fun setUp() {
        hiltRule.inject()
    }


    @Test
    fun textInput_translate_swapLanguages() = runBlocking<Unit> {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val client = fakeClient as FakeTranslateClient

        val inputText = "Hello world"
        composeRule.onNodeWithTag(TestTags.translateTextField).performTextInput(inputText)

        composeRule.onNodeWithText(context.getString(R.string.translate), ignoreCase = true)
            .performClick()

        composeRule.onNodeWithTag(TestTags.translatedTextField, useUnmergedTree = true)
            .assertIsDisplayed()

        composeRule.onNodeWithText(client.translatedText).assertIsDisplayed()

        composeRule.onNodeWithContentDescription(context.getString(R.string.swap_languages))
            .performClick()

        composeRule.onNodeWithContentDescription(TestTags.fromDropdown).onChildren().filter(
            hasTextExactly("Turkish")
        ).onFirst().assertIsDisplayed()

        composeRule.onNodeWithContentDescription(TestTags.toDropdown).onChildren().filter(
            hasTextExactly("English")
        ).onFirst().assertIsDisplayed()

        composeRule.onNodeWithTag(TestTags.translatedTextField, useUnmergedTree = true).onChildren()
            .filter(hasTextExactly(client.translatedText)).onFirst().assertIsDisplayed()

        composeRule.onNodeWithTag(TestTags.translatedTextField, useUnmergedTree = true).onChildren()
            .filter(hasTextExactly(inputText)).onFirst().assertIsDisplayed()

    }

}