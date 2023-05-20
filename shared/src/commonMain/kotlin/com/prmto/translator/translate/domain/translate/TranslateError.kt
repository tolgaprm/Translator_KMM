package com.prmto.translator.translate.domain.translate

enum class TranslateError {
    SERVICE_UNAVAILABLE,
    ClIENT_ERROR,
    SERVER_ERROR,
    UNKNOWN_ERROR,
    LANGUAGE_NOT_SUPPORTED
}

class TranslateException(val error: TranslateError) : Exception(
    "An error occurred while translating: $error"
)