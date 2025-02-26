package app.benchmate.repositories.di

import app.benchmate.repositories.player.PlayerRepository
import app.benchmate.repositories.player.RealPlayerRepository
import android.app.Application
import android.content.Context
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import app.benchmate.repositories.db.DatabaseDriverFactory
import app.benchmate.repositories.di.RepositoriesComponent

//@AppScope
@Component
abstract class AppComponent(
    @get:Provides val application: Application
) : RepositoriesComponent {

//    @Provides
//    protected fun databaseDriverFactory(): DatabaseDriverFactory = DatabaseDriverFactory(context)

    @Provides
    fun context(application: Application): Context = application.applicationContext

    protected val RealPlayerRepository.bind: PlayerRepository
        @Provides get() = this

    companion object {
        fun create(application: Application): AppComponent = AppComponent(application)
    }
}