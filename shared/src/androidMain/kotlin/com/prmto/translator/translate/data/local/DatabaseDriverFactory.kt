package com.prmto.translator.translate.data.local

import android.content.Context
import com.prmto.translator.database.TranslatorDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(
    private val context: Context
) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(TranslatorDatabase.Schema, context, "translate.db")
    }
}