package com.prmto.translator.testing

import com.prmto.translator.core.domain.util.CommonFlow
import com.prmto.translator.core.domain.util.toCommonFlow
import com.prmto.translator.translate.domain.history.HistoryDataSource
import com.prmto.translator.translate.domain.history.HistoryItem
import kotlinx.coroutines.flow.MutableStateFlow

class FakeHistoryDataSource : HistoryDataSource {

    private val _data = MutableStateFlow<List<HistoryItem>>(emptyList())
    override fun getHistory(): CommonFlow<List<HistoryItem>> {
        return _data.toCommonFlow()
    }

    override suspend fun insertHistoryItem(item: HistoryItem) {
        _data.value += item
    }

}