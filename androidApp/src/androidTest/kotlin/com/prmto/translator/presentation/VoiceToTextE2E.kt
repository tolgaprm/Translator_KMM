package com.prmto.translator.presentation

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.rule.GrantPermissionRule
import com.prmto.translator.android.MainActivity
import com.prmto.translator.android.R
import com.prmto.translator.android.di.AppModule
import com.prmto.translator.android.voice_to_text.di.VoiceToTextModule
import com.prmto.translator.translate.data.remote.FakeTranslateClient
import com.prmto.translator.translate.domain.translate.TranslateClient
import com.prmto.translator.voice_to_text.data.FakeVoiceToTextParser
import com.prmto.translator.voice_to_text.domain.VoiceToTextParser
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject


@HiltAndroidTest
@UninstallModules(AppModule::class, VoiceToTextModule::class)
class VoiceToTextE2E {

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
    fun recordAndTranslate() = runBlocking<Unit> {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val parser = fakeVoiceParser as FakeVoiceToTextParser
        val client = fakeClient as FakeTranslateClient

        composeRule.onNodeWithContentDescription(
            context.getString(R.string.record_audio)
        ).performClick()

        composeRule.onNodeWithContentDescription(
            context.getString(R.string.record_audio)
        ).performClick()

        composeRule.onNodeWithContentDescription(
            context.getString(R.string.stop_recording)
        ).performClick()

        composeRule.onNodeWithText(parser.voiceResult)
            .assertIsDisplayed()

        composeRule.onNodeWithContentDescription(
            context.getString(R.string.apply)
        ).performClick()

        composeRule.onNodeWithText(parser.voiceResult)
            .assertIsDisplayed()

        composeRule.onNodeWithText(context.getString(R.string.translate), ignoreCase = true)
            .performClick()

        composeRule.onNodeWithText(parser.voiceResult)
            .assertIsDisplayed()

        composeRule.onNodeWithText(client.translatedText)
            .assertIsDisplayed()


    }

}