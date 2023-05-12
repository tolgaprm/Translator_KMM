package com.prmto.translator.di

import com.prmto.translator.translate.data.local.FakeHistoryDataSource
import com.prmto.translator.translate.data.remote.FakeTranslateClient
import com.prmto.translator.translate.domain.history.HistoryDataSource
import com.prmto.translator.translate.domain.translate.TranslateClient
import com.prmto.translator.translate.domain.translate.TranslateUseCase
import com.prmto.translator.voice_to_text.data.FakeVoiceToTextParser
import com.prmto.translator.voice_to_text.domain.VoiceToTextParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    fun provideFakeTranslateClient(): TranslateClient = FakeTranslateClient()

    @Provides
    @Singleton
    fun provideFakeHistoryDataSource(): HistoryDataSource = FakeHistoryDataSource()

    @Provides
    @Singleton
    fun provideTranslateUseCase(
        client: TranslateClient,
        dataSource: HistoryDataSource
    ): TranslateUseCase = TranslateUseCase(client, dataSource)

    @Provides
    @Singleton
    fun provideFakeVoiceToTextParser(): VoiceToTextParser = FakeVoiceToTextParser()


}