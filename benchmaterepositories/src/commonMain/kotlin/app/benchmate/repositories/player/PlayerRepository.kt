package app.benchmate.repositories.player

import app.benchmate.repositories.db.Database
import app.benchmate.repositories.db.DatabaseDriverFactory
import app.benchmate.repositories.db.entity.toDomain
import app.benchmate.repositories.models.Player
import app.benchmate.repositories.models.PlayerStatus

interface PlayerRepository {

    suspend fun getAllPlayers(): List<Player>

    suspend fun addPlayer(
        playerId: String,
        firstName: String,
        number: Int,
        playerStatus: PlayerStatus,
        onBenchCount: Int
    )
}

class RealPlayerRepository(databaseDriverFactory: DatabaseDriverFactory): PlayerRepository {

    private val database = Database(databaseDriverFactory)

    override suspend fun getAllPlayers(): List<Player> {
        return database.getAllPlayers().map {
            it.toDomain()
        }
    }

    override suspend fun addPlayer(
        playerId: String,
        firstName: String,
        number: Int,
        playerStatus: PlayerStatus,
        onBenchCount: Int
    ) {
        database.addPlayerToDatabase(
            playerId = playerId,
            firstName = firstName,
            number = number,
            playerStatus = playerStatus,
            onBenchCount = onBenchCount
        )
    }
}