package app.benchmate.repositories.common.db.entity

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
    val playerStatus: PlayerStatus,
    @SerialName("on_bench_count")
    val onBenchCount: Int
)