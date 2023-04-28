package com.prmto.translator.translate.data.local

import com.prmto.translator.database.TranslatorDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DatabaseDriverFactory {

    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(TranslatorDatabase.Schema, "translate.db")
    }
}