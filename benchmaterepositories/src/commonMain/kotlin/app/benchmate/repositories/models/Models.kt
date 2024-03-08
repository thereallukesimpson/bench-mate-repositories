package app.benchmate.repositories.models

data class Team(
    val teamId: String,
    val teamName: String,
    val players: List<Player>
)

data class Player(
    val playerId: String,
    val firstName: String,
    val lastName: String,
    val games: List<Game>,
    val playerStatus: PlayerStatus
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
    val gameId: String,
    val startTime: Int = 0, // TODO use TimeMark or similar
    val endTime: Int // TODO use TimeMark or similar
)

enum class PlayerStatus {
    NONE,
    BENCH
}

