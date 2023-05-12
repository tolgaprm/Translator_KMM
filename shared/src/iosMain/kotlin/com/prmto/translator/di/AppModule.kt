package com.prmto.translator.di

import com.prmto.translator.database.TranslatorDatabase
import com.prmto.translator.translate.data.history.SqlDelightHistoryDataSource
import com.prmto.translator.translate.data.local.DatabaseDriverFactory
import com.prmto.translator.translate.data.remote.HttpClientFactory
import com.prmto.translator.translate.data.translate.KtorTranslateClient
import com.prmto.translator.translate.domain.history.HistoryDataSource
import com.prmto.translator.translate.domain.translate.TranslateClient
import com.prmto.translator.translate.domain.translate.TranslateUseCase
import com.prmto.translator.voice_to_text.domain.VoiceToTextParser

interface AppModule {
    val historyDataSource: HistoryDataSource
    val translateClient: TranslateClient
    val translateUseCase: TranslateUseCase
    val voiceParser: VoiceToTextParser
}

class AppModuleImpl(
    parser: VoiceToTextParser
) : AppModule {

    override val historyDataSource: HistoryDataSource by lazy {
        SqlDelightHistoryDataSource(
            TranslatorDatabase(
                DatabaseDriverFactory().createDriver()
            )
        )
    }

    override val translateClient: TranslateClient by lazy {
        KtorTranslateClient(HttpClientFactory().create())
    }

    override val translateUseCase: TranslateUseCase by lazy {
        TranslateUseCase(translateClient, historyDataSource)
    }
    override val voiceParser = parser
}