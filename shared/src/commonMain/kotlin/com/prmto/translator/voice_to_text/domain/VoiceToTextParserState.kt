package com.prmto.translator.voice_to_text.domain

data class VoiceToTextParserState(
    val result: String = "",
    val error: String? = null,
    val powerRatio: Float = 0f, // This power ratio is a value between 0.0 and 1.0,
    // it will describe how loud the user is speaking into the microphone.
    val isSpeaking: Boolean = false,
)
