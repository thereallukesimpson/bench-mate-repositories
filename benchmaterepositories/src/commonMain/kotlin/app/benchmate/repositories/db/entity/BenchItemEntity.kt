package app.benchmate.repositories.db.entity

import app.benchmate.repositories.models.BenchItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.TimeMark

@Serializable
data class BenchItemEntity(
    @SerialName("player_id")
    val playerId: String,
    @SerialName("game_id")
    val gameId: String? = null,
    @SerialName("start_time")
    val startTime: TimeMark,
)

fun BenchItemEntity.toDomain(): BenchItem {
    return BenchItem(
        playerId = playerId,
        gameId = gameId,
        startTime = startTime
    )
}