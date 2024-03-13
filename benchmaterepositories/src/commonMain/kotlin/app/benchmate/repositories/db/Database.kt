package app.benchmate.repositories.db

import app.benchmate.repositories.db.entity.PlayerEntity
import app.benchmate.repositories.models.PlayerStatus
import app.cash.sqldelight.EnumColumnAdapter
import appbenchmaterepositories.db.Player

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

    internal fun getAllPlayers(): List<PlayerEntity> {
        return dbQuery.selectAllPlayers().executeAsList().map {
                PlayerEntity(
                    playerId = it.playerId,
                    firstName = it.firstName,
                    number = it.number.toInt(),
                    playerStatus = it.playerStatus,
                    onBenchCount = it.onBenchCount?.toInt()
                )
        }
    }

    internal fun updatePlayerStatus(playerId: String, status: PlayerStatus, onBenchCount: Int) {
        return dbQuery.updatePlayerStatus(playerId = playerId, playerStatus = status, onBenchCount = onBenchCount.toLong())
    }

    internal fun clearBenchCountAndPlayerStatus() {
        return dbQuery.clearBenchCountAndPlayerStatus(playerStatus = PlayerStatus.NONE, onBenchCount = 0)
    }
}