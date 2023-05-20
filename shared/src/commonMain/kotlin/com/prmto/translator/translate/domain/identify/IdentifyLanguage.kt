package com.prmto.translator.translate.domain.identify

interface IdentifyLanguage {
    suspend fun execute(
        text: String,
        onSuccess: (String) -> Unit,
        onError: (Throwable) -> Unit
    )
}