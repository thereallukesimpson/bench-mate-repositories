package app.benchmate.repositories.di

import me.tatarka.inject.annotations.Provides
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.KmpComponentCreate
import app.benchmate.repositories.player.PlayerRepository
import app.benchmate.repositories.db.DatabaseDriverFactory

//@Component
interface RepositoriesComponent {

    val playerRepository: PlayerRepository

    val databaseDriverFactory: DatabaseDriverFactory
        @Provides get() = DatabaseDriverFactory.createDriver()

//    abstract val playerRepository: PlayerRepository

//    companion object
}

//@KmpComponentCreate
//expect fun createKmp(): RepositoriesComponent
//expect fun RepositoriesComponent.Companion.create(): RepositoriesComponent

//val repositoriesComponent = RepositoriesComponent::class.create()