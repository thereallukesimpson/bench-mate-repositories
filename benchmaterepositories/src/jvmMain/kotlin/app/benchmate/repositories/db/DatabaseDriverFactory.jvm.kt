package app.benchmate.repositories.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
//        return JdbcSqliteDriver("jdbc:sqlite:") // Need to get path to DB, this creates temp DB

        val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:")
        AppDatabase.Schema.create(driver)
        return driver
    }
}