package app.benchmate.repositories

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform