package app.benchmate.repositories.db

import app.benchmate.repositories.models.PlayerStatus
import appbenchmaterepositories.db.Player
import com.squareup.sqldelight.EnumColumnAdapter

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(
        driver = databaseDriverFactory.createDriver(),
        PlayerAdapter = Player.Adapter(
            playerStatusAdapter = EnumColumnAdapter<PlayerStatus>()
        )
    )
    private val dbQuery = database.appDatabaseQueries

    internal fun addPlayerToDatabase(
        playerId: String,
        firstName: String,
        number: Int,
        playerStatus: PlayerStatus,
        onBenchCount: Int
    ) {
        dbQuery.insertPlayer(
            playerId = playerId,
            firstName = firstName,
            number = number.toLong(),
            playerStatus = playerStatus,
            onBenchCount = onBenchCount.toLong()
        )
    }
}