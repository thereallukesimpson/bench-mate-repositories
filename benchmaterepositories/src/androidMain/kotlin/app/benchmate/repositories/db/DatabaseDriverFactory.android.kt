package app.benchmate.repositories.db

import android.content.Context
import app.cash.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
//        return AndroidSqliteDriver(AppDatabase.Schema, context, "AppDatabase.db")
        TODO()
    }
}