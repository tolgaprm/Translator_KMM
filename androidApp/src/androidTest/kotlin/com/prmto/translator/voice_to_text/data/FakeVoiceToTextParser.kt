package com.prmto.translator.voice_to_text.data

import com.prmto.translator.core.domain.util.CommonStateFlow
import com.prmto.translator.core.domain.util.toCommonStateFlow
import com.prmto.translator.voice_to_text.domain.VoiceToTextParser
import com.prmto.translator.voice_to_text.domain.VoiceToTextParserState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class FakeVoiceToTextParser : VoiceToTextParser {

    private val _state = MutableStateFlow(VoiceToTextParserState())
    override val state: CommonStateFlow<VoiceToTextParserState>
        get() = _state.toCommonStateFlow()

    val voiceResult = "test result"
    override fun startListening(languageCode: String) {
        _state.update {
            it.copy(
                result = "",
                isSpeaking = true
            )
        }
    }

    override fun stopListening() {
        _state.update {
            it.copy(
                result = voiceResult,
                isSpeaking = false
            )
        }
    }

    override fun cancel() = Unit

    override fun reset() {
      _state.update { VoiceToTextParserState() }
    }
}