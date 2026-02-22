package app.benchmate.repositories.models

import kotlin.time.DurationUnit
import kotlin.time.TimeMark
import kotlin.time.TimeSource
import kotlin.time.toDuration

object TimeMarkConverter {
    fun TimeMark.toLong(): Long {
        // Get the duration since the mark was created
        val durationSinceMark = this.elapsedNow()
        // Get the current time in milliseconds since the epoch
        val currentTimeMillis = System.currentTimeMillis()
        // Subtract the duration to get the time when the mark was created
        return currentTimeMillis - durationSinceMark.inWholeMilliseconds
    }

    fun Long.toTimeMark(): TimeMark {
        // Get the current time in milliseconds since the epoch
        val currentTimeMillis = System.currentTimeMillis()
        // Calculate the duration since the mark was created
        val durationSinceMark = (currentTimeMillis - this).toDuration(DurationUnit.MILLISECONDS)
        // Create a new TimeMark at the calculated time
        return TimeSource.Monotonic.markNow() - durationSinceMark
    }
}