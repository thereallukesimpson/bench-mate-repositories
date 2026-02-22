package app.benchmate.repositories

import app.benchmate.repositories.db.Database
import app.benchmate.repositories.db.DatabaseDriverFactory
import app.benchmate.repositories.models.PlayerStatus
import app.benchmate.repositories.models.TimeMarkConverter
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.time.TimeSource

class BenchItemIntegrationTest {

    private fun createDatabase(): Database = Database(DatabaseDriverFactory())

    @Test
    fun benchItemFullFlow() {
        val db = createDatabase()

        // Add a player
        db.addPlayerToDatabase(
            firstName = "Alice",
            number = 7,
            playerStatus = PlayerStatus.NONE,
            onBenchCount = 0
        )
        val playerId = db.getAllPlayers().first().playerId

        // addBenchItem → getAllPlayers → assert benchItems.size == 1
        db.addBenchItem(
            playerId = playerId,
            startTime = with(TimeMarkConverter) { TimeSource.Monotonic.markNow().toLong() }
        )
        var players = db.getAllPlayers()
        assertEquals(1, players.first().benchItems.size)

        // pauseBenchTime → getAllPlayers → assert benchItems[0].endTime != null
        db.pauseBenchTime(
            playerId = playerId,
            endTime = with(TimeMarkConverter) { TimeSource.Monotonic.markNow().toLong() }
        )
        players = db.getAllPlayers()
        assertNotNull(players.first().benchItems[0].endTime)

        // continueBenchTime → getAllPlayers → assert benchItems.size == 2
        db.addBenchItem(
            playerId = playerId,
            startTime = with(TimeMarkConverter) { TimeSource.Monotonic.markNow().toLong() }
        )
        players = db.getAllPlayers()
        assertEquals(2, players.first().benchItems.size)

        // clearBenchItems → getAllPlayers → assert benchItems.isEmpty()
        db.clearBenchItems()
        players = db.getAllPlayers()
        assertEquals(true, players.first().benchItems.isEmpty())
    }
}
