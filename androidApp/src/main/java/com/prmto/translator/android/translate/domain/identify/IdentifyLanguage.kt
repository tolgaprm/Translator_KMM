package com.prmto.translator.android.translate.domain.identify

import com.google.mlkit.nl.languageid.LanguageIdentifier
import com.prmto.translator.translate.domain.identify.IdentifyLanguage
import javax.inject.Inject

class IdentifyLanguageImpl @Inject constructor(
    private val languageIdentifier: LanguageIdentifier
) : IdentifyLanguage {
    override suspend fun execute(
        text: String,
        onSuccess: (String) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        languageIdentifier.identifyLanguage(text)
            .addOnSuccessListener { languageCode ->
                onSuccess(languageCode)
            }
            .addOnFailureListener { exception ->
                onError(exception)
            }
    }
}