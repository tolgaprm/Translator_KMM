package com.prmto.translator.translate.domain.translate

enum class TranslateError {
    SERVICE_UNAVAILABLE,
    ClIENT_ERROR,
    SERVER_ERROR,
    UNKNOWN_ERROR
}

class TranslateException(val error: TranslateError) : Exception(
    message = "An error occurred while translating: $error"
)