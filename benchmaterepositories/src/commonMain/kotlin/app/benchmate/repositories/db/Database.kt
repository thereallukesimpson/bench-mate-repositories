package app.benchmate.repositories.db

import app.benchmate.repositories.db.entity.BenchItemEntity
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
                onBenchCount = it.onBenchCount?.toInt(),
                benchItems = getBenchItemsForPlayer(it.playerId)
            )
        }
    }

    internal fun updatePlayerStatus(playerId: String, status: PlayerStatus, onBenchCount: Int) {
        return dbQuery.updatePlayerStatus(playerId = playerId, playerStatus = status, onBenchCount = onBenchCount.toLong())
    }

    internal fun clearBenchCountAndPlayerStatus() {
        return dbQuery.clearBenchCountAndPlayerStatus(playerStatus = PlayerStatus.NONE, onBenchCount = 0)
    }

    @OptIn(ExperimentalUuidApi::class)
    internal fun addBenchItem(playerId: String, startTime: Long, gameId: String? = null) {
        dbQuery.insertBenchItem(
            benchItemId = Uuid.random().toString(),
            playerId = playerId,
            gameId = gameId,
            startTime = startTime
        )
    }

    internal fun getBenchItemsForPlayer(playerId: String): List<BenchItemEntity> {
        return dbQuery.selectBenchItemsByPlayerId(playerId).executeAsList().map {
            BenchItemEntity(
                benchItemId = it.benchItemId,
                playerId = it.playerId,
                gameId = it.gameId,
                startTime = it.startTime,
                endTime = it.endTime,
            )
        }
    }

    internal fun pauseBenchTime(playerId: String, endTime: Long) {
        val openItem = dbQuery.selectLatestOpenBenchItemForPlayer(playerId).executeAsOneOrNull()
            ?: return
        dbQuery.updateBenchItemEndTime(endTime = endTime, benchItemId = openItem.benchItemId)
    }

    internal fun clearBenchItems() {
        dbQuery.clearAllBenchItems()
    }
}
