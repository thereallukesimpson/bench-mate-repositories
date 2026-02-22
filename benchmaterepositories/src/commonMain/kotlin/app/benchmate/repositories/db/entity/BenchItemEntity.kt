package app.benchmate.repositories.db.entity

import app.benchmate.repositories.models.BenchItem
import app.benchmate.repositories.models.TimeMarkConverter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BenchItemEntity(
    @SerialName("bench_item_id")
    val benchItemId: String,
    @SerialName("player_id")
    val playerId: String,
    @SerialName("game_id")
    val gameId: String? = null,
    @SerialName("start_time")
    val startTime: Long,
    @SerialName("end_time")
    val endTime: Long? = null,
)

fun BenchItemEntity.toDomain(): BenchItem {
    return BenchItem(
        playerId = playerId,
        gameId = gameId,
        startTime = with(TimeMarkConverter) { startTime.toTimeMark() },
        endTime = endTime?.let { with(TimeMarkConverter) { it.toTimeMark() } },
    )
}
