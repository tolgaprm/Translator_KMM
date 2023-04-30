package com.prmto.translator.core.presentation

import com.prmto.translator.core.domain.language.Language

expect class UiLanguage {
    val language: Language

    companion object {
        fun byCode(langCode: String): UiLanguage
        val allLanguages: List<UiLanguage>
    }
}