package app.benchmate.repositories.di

import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.KmpComponentCreate
import app.benchmate.repositories.player.PlayerRepository

@Component
abstract class RepositoriesComponent {
    abstract val playerRepository: PlayerRepostiory
}

@KmpComponentCreate
expect fun createKmp(): RepositoriesComponent