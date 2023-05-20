package com.prmto.translator.android.di

import android.app.Application
import com.google.mlkit.nl.languageid.LanguageIdentification
import com.google.mlkit.nl.languageid.LanguageIdentifier
import com.prmto.translator.android.translate.domain.identify.IdentifyLanguageImpl
import com.prmto.translator.database.TranslatorDatabase
import com.prmto.translator.translate.data.history.SqlDelightHistoryDataSource
import com.prmto.translator.translate.data.local.DatabaseDriverFactory
import com.prmto.translator.translate.data.remote.HttpClientFactory
import com.prmto.translator.translate.data.translate.KtorTranslateClient
import com.prmto.translator.translate.domain.history.HistoryDataSource
import com.prmto.translator.translate.domain.identify.IdentifyLanguage
import com.prmto.translator.translate.domain.translate.TranslateClient
import com.prmto.translator.translate.domain.translate.TranslateUseCase
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClientFactory().create()
    }

    @Provides
    @Singleton
    fun provideTranslateClient(
        httpClient: HttpClient
    ): TranslateClient {
        return KtorTranslateClient(httpClient)
    }

    @Provides
    @Singleton
    fun provideDatabaseDriver(
        app: Application
    ): SqlDriver {
        return DatabaseDriverFactory(app).createDriver()
    }

    @Provides
    @Singleton
    fun provideHistoryDataSource(
        sqlDriver: SqlDriver,
    ): HistoryDataSource {
        return SqlDelightHistoryDataSource(
            db = TranslatorDatabase(sqlDriver)
        )
    }

    @Provides
    @Singleton
    fun provideTranslateUseCase(
        client: TranslateClient,
        dataSource: HistoryDataSource
    ): TranslateUseCase {
        return TranslateUseCase(
            client = client,
            historyDataSource = dataSource
        )
    }

    @Provides
    @Singleton
    fun provideLanguageIdentifier(): LanguageIdentifier {
        return LanguageIdentification.getClient()
    }

    @Provides
    @Singleton
    fun provideIdentifyLanguage(
        languageIdentifier: LanguageIdentifier
    ): IdentifyLanguage {
        return IdentifyLanguageImpl(languageIdentifier)
    }


}