package org.msfox.batmanmoviesapp.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import org.msfox.batmanmoviesapp.SearchMoviesApp
import javax.inject.Singleton

@Singleton
@Component(
    modules =
        [AndroidInjectionModule::class,
        AppModule::class,
        ViewModelModule::class,
        ActivitiesModule::class]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(searchMoviesApp: SearchMoviesApp)
}
