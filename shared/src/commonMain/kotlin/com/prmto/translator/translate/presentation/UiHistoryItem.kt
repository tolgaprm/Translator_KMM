package com.prmto.translator.translate.presentation

import com.prmto.translator.core.presentation.UiLanguage

data class UiHistoryItem(
    val id: Long?,
    val fromText: String,
    val toText: String,
    val fromLanguage: UiLanguage,
    val toLanguage: UiLanguage
)