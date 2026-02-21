# BenchMate Repositories

This project provides a set of repositories for the BenchMate application.
It is a Kotlin Multiplatform project that currently targets Android but may be used in iOS in the future.

## Tech Stack

* **Kotlin Multiplatform:** Write code once and share it across different platforms.
* **SQLDelight:** Generate type-safe Kotlin APIs from your SQL statements.
* **Ktor:** A powerful and asynchronous framework for building clients and servers.
* **Kotlinx Serialization:** A multiplatform library for serializing and deserializing data.
* **Kotlinx Coroutines:** For asynchronous programming.
* **Kotlinx DateTime:** A multiplatform library for working with dates and times.

## Platforms

* Android
* iOS (in the future)

## Usage

To use this library in your project, add the following to your `build.gradle.kts` file:

```kotlin
repositories {
    mavenLocal()
}

dependencies {
    implementation("app.benchmate:benchmaterepositories:0.0.9")
}
```

Then build and deploy this project to maven local with the following command.

```
./gradlew deployToMavenLocal
```
