package com.prmto.translator.di

import com.prmto.translator.testing.FakeHistoryDataSource
import com.prmto.translator.testing.FakeTranslateClient
import com.prmto.translator.testing.FakeVoiceToTextParser
import com.prmto.translator.translate.domain.translate.TranslateUseCase
import com.prmto.translator.voice_to_text.domain.VoiceToTextParser

class TestAppModule : AppModule {
    override val historyDataSource = FakeHistoryDataSource()
    override val translateClient = FakeTranslateClient()
    override val translateUseCase = TranslateUseCase(translateClient, historyDataSource)
    override val voiceParser: VoiceToTextParser = FakeVoiceToTextParser()
}