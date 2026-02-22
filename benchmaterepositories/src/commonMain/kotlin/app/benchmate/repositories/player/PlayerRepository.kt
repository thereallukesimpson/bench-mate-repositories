package app.benchmate.repositories.player

import app.benchmate.repositories.db.Database
import app.benchmate.repositories.db.DatabaseDriverFactory
import app.benchmate.repositories.db.entity.toDomain
import app.benchmate.repositories.models.Player
import app.benchmate.repositories.models.PlayerStatus
import app.benchmate.repositories.models.TimeMarkConverter
import kotlin.time.TimeSource

interface PlayerRepository {

    suspend fun getAllPlayers(): List<Player>

    suspend fun addPlayer(
        firstName: String,
        number: Int,
        playerStatus: PlayerStatus,
        onBenchCount: Int
    )

    suspend fun updatePlayerStatus(
        playerId: String,
        playerStatus: PlayerStatus,
        onBenchCount: Int
    )

    suspend fun clearBenchCountAndPlayerStatus()

    suspend fun addBenchItem(playerId: String)

    suspend fun pauseBenchTime(playerId: String)

    suspend fun clearBenchItems()
}

class RealPlayerRepository(databaseDriverFactory: DatabaseDriverFactory): PlayerRepository {

    private val database = Database(databaseDriverFactory)

    override suspend fun getAllPlayers(): List<Player> {
        return database.getAllPlayers().map {
            it.toDomain()
        }
    }

    override suspend fun addPlayer(
        firstName: String,
        number: Int,
        playerStatus: PlayerStatus,
        onBenchCount: Int
    ) {
        database.addPlayerToDatabase(
            firstName = firstName,
            number = number,
            playerStatus = playerStatus,
            onBenchCount = onBenchCount
        )
    }

    override suspend fun updatePlayerStatus(playerId: String, playerStatus: PlayerStatus, onBenchCount: Int) {
       database.updatePlayerStatus(playerId, playerStatus, onBenchCount = onBenchCount)
    }

    override suspend fun clearBenchCountAndPlayerStatus() {
        database.clearBenchCountAndPlayerStatus()
    }

    override suspend fun addBenchItem(playerId: String) {
        database.addBenchItem(
            playerId = playerId,
            startTime = with(TimeMarkConverter) { TimeSource.Monotonic.markNow().toLong() }
        )
    }

    override suspend fun pauseBenchTime(playerId: String) {
        database.pauseBenchTime(
            playerId = playerId,
            endTime = with(TimeMarkConverter) { TimeSource.Monotonic.markNow().toLong() }
        )
    }

    override suspend fun clearBenchItems() {
        database.clearBenchItems()
    }
}
