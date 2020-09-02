package org.msfox.batmanmoviesapp.di

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import org.msfox.batmanmoviesapp.api.MovieService
import org.msfox.batmanmoviesapp.api.NetworkResponseAdapterFactory
import org.msfox.batmanmoviesapp.api.SearchMovies
import org.msfox.batmanmoviesapp.db.AppDb
import org.msfox.batmanmoviesapp.db.DescriptionDao
import org.msfox.batmanmoviesapp.db.SearchMoviesDao
import org.msfox.batmanmoviesapp.vm.DescriptionViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideSearchMovies(): MovieService {
        return Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .build()
            .create(MovieService::class.java)
    }
    @Singleton
    @Provides
    fun provideContext(application: Application): Context{
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): AppDb {
        return Room
            .databaseBuilder(app, AppDb::class.java, "appdb.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideSearchMoviesDao(db: AppDb): SearchMoviesDao {
        return db.searchMoviesDao()
    }

    @Singleton
    @Provides
    fun provideDescriptionDao(db: AppDb): DescriptionDao {
        return db.descriptionDao()
    }

}