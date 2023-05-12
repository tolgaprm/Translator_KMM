package com.prmto.translator.translate.data.remote

import com.prmto.translator.core.domain.language.Language
import com.prmto.translator.translate.domain.translate.TranslateClient


open class FakeTranslateClient : TranslateClient {

    var translatedText = "test translation"
    override suspend fun translate(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): String {
        return translatedText
    }

}