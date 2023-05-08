package com.prmto.translator.voice_to_text.domain

import com.prmto.translator.core.domain.util.CommonStateFlow

interface VoiceToTextParser {
    val state: CommonStateFlow<VoiceToTextParserState>
    fun startListening(languageCode: String)
    fun stopListening()
    fun cancel() // Cancel voice recognition request
    fun reset()
}