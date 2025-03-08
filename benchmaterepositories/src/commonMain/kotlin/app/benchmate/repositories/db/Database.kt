package app.benchmate.repositories.db

import app.benchmate.repositories.db.entity.PlayerEntity
import app.benchmate.repositories.models.PlayerStatus
import app.cash.sqldelight.EnumColumnAdapter
import appbenchmaterepositories.db.Player
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(
        driver = databaseDriverFactory.createDriver(),
        PlayerAdapter = Player.Adapter(
            playerStatusAdapter = EnumColumnAdapter<PlayerStatus>()
        )
    )
    private val dbQuery = database.appDatabaseQueries

    @OptIn(ExperimentalUuidApi::class)
    internal fun addPlayerToDatabase(
        firstName: String,
        number: Int,
        playerStatus: PlayerStatus,
        onBenchCount: Int
    ) {
        dbQuery.insertPlayer(
            playerId = Uuid.random().toString(),
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