package com.prmto.translator.di

import com.prmto.translator.database.TranslatorDatabase
import com.prmto.translator.translate.data.history.SqlDelightHistoryDataSource
import com.prmto.translator.translate.data.local.DatabaseDriverFactory
import com.prmto.translator.translate.data.remote.HttpClientFactory
import com.prmto.translator.translate.data.translate.KtorTranslateClient
import com.prmto.translator.translate.domain.history.HistoryDataSource
import com.prmto.translator.translate.domain.translate.TranslateClient
import com.prmto.translator.translate.domain.translate.TranslateUseCase

class AppModule {

    val historyDataSource: HistoryDataSource by lazy {
        SqlDelightHistoryDataSource(
            TranslatorDatabase(
                DatabaseDriverFactory().createDriver()
            )
        )
    }

    private val translateClient: TranslateClient by lazy {
        KtorTranslateClient(HttpClientFactory().create())
    }

    val translateUseCase: TranslateUseCase by lazy {
        TranslateUseCase(translateClient, historyDataSource)
    }
}