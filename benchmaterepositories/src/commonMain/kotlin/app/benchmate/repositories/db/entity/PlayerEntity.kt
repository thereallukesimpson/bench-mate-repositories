package app.benchmate.repositories.db.entity

import app.benchmate.repositories.models.Player
import app.benchmate.repositories.models.PlayerStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayerEntity(
    @SerialName("player_id")
    val playerId: String,
    @SerialName("first_name")
    val firstName: String,
    @SerialName("number")
    val number: Int,
    @SerialName("player_status")
    val playerStatus: PlayerStatus?,
    @SerialName("on_bench_count")
    val onBenchCount: Int?,
    @SerialName("bench_items")
    val benchItems: List<BenchItemEntity>
)

fun PlayerEntity.toDomain(): Player {
    return Player(
        playerId = playerId,
        firstName = firstName,
        number = number,
        playerStatus = playerStatus,
        onBenchCount = onBenchCount,
        benchItems = benchItems.map { it.toDomain() }
    )
}