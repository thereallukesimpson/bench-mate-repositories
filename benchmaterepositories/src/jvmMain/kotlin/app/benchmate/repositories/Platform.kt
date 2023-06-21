package app.benchmate.repositories

class JvmPlatform : Platform {
    override val name: String = "JVM Platform"
}

actual fun getPlatform(): Platform = JvmPlatform()