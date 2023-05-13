package com.prmto.translator.translate.data.history

import com.prmto.translator.core.domain.util.CommonFlow
import com.prmto.translator.core.domain.util.toCommonFlow
import com.prmto.translator.database.TranslatorDatabase
import com.prmto.translator.translate.domain.history.HistoryDataSource
import com.prmto.translator.translate.domain.history.HistoryItem
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class SqlDelightHistoryDataSource(
    db: TranslatorDatabase
) : HistoryDataSource {

    private val queries = db.translateQueries
    override fun getHistory(): CommonFlow<List<HistoryItem>> {
        return queries
            .getHistory()
            .asFlow()
            .mapToList()
            .map { history ->
                history.map { it.toHistoryItem() }
            }.toCommonFlow()
    }

    override suspend fun insertHistoryItem(item: HistoryItem) {
        queries.insertHistoryEntity(
            id = item.id,
            fromLanguageCode = item.fromLanguageCode,
            fromText = item.fromText,
            toLanguageCode = item.toLanguageCode,
            toText = item.toText,
            timestamp = Clock.System.now().toEpochMilliseconds()
        )
    }

    override suspend fun deleteHistoryItem(itemId: Long) {
        queries.deleteHistoryEntity(itemId)
    }
}