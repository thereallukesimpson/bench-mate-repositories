package app.benchmate.repositories.db

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return JdbcSqliteDriver("jdbc:sqlite:test.db") as SqlDriver
    }
}