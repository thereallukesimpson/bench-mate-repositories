# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

All commands run from the `bench-mate-repositories/` directory.

```bash
./gradlew build                  # Build all targets
./gradlew test                   # Run all unit tests
./gradlew deployToMavenLocal     # Publish to Maven Local (required after changes, for the Android app to pick them up)
```

The Android app consumes this library as `app.benchmate:benchmaterepositories` from Maven Local. Always run `deployToMavenLocal` after making changes here before building/testing the Android app.

Current published version: `0.1.0` (declared in `benchmaterepositories/build.gradle.kts`).

## Architecture

Kotlin Multiplatform library targeting Android (iOS support is stubbed but not active — iOS targets are commented out in the build config).

**Layers:**

```
SQLDelight generated API
    ↓
Database.kt  (internal wrapper — maps query results to entity objects)
    ↓
PlayerRepository interface  +  RealPlayerRepository implementation
    ↓
Domain models (Player, BenchItem, PlayerStatus)  ← consumed by the Android app
```

- **SQLDelight** generates type-safe Kotlin from `AppDatabase.sq`. The schema has two tables: `Player` and `BenchItem`. Queries are defined in the `.sq` file and accessed via the generated `AppDatabase` class inside `Database.kt`.
- **`DatabaseDriverFactory`** is an `expect` class. The `androidMain` actual uses `AndroidSqliteDriver`. A JVM actual exists for tests.
- **`PlayerRepository`** is an interface. `RealPlayerRepository` is the only implementation and is internal to this module. Consumers interact only via the interface.
- **Domain models** (`Models.kt`) are the public API surface of this library. `Player` contains a `List<BenchItem>` where each `BenchItem` carries `startTime: TimeMark` and `endTime: TimeMark?`. `TimeMark` values are stored in the DB as `Long` epoch offsets via `TimeMarkConverter`.
- **`TimeMarkConverter`** handles `TimeMark ↔ Long` serialization. Bench durations are derived at read time from elapsed `TimeMark` values, not stored as pre-computed durations.
