package com.prmto.translator.translate.data.remote

import com.prmto.translator.core.domain.language.Language
import com.prmto.translator.translate.domain.translate.TranslateError
import com.prmto.translator.translate.domain.translate.TranslateException

class FakeErrorTranslateClient : FakeTranslateClient() {
    override suspend fun translate(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): String {
        throw TranslateException(TranslateError.UNKNOWN_ERROR)
    }

}