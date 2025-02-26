package app.benchmate.repositories.db

import app.cash.sqldelight.db.SqlDriver
import me.tatarka.inject.annotations.Inject

//@Inject
expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}