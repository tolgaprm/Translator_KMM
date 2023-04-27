package com.prmto.translator.translate.data.translate

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class TranslateDto(
    @SerialName("q") val textToTranslate: String,
    @SerialName("source") val sourceLanguageCode: String,
    @SerialName("target") val targetLanguageCode: String
)
