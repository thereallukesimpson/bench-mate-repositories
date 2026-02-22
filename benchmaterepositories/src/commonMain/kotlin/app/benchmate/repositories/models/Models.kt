package app.benchmate.repositories.models

import kotlin.time.TimeMark

data class Team(
    val teamId: String,
    val teamName: String,
    val players: List<Player>
)

data class Player(
    val playerId: String,
    val firstName: String,
    val number: Int,
    val playerStatus: PlayerStatus?,
    val benchItems: List<BenchItem>,
    val onBenchCount: Int?, // TODO deprecate this field in favour of benchItems.size
//    val games: List<Game> // N/A right now
)

data class Game(
    val gameId: String,
    val season: String,
    val round: Int,
    val benchId: String,
    val players: List<Player>
)

data class Bench(
    val benchId: String,
    val playerBenchTime: List<BenchItem>
)

data class BenchItem(
    val playerId: String,
    val gameId: String? = null,
    val startTime: TimeMark,
    val endTime: TimeMark? = null,
)

enum class PlayerStatus {
    NONE,
    BENCH
}

