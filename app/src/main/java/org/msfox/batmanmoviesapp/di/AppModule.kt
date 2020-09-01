package org.msfox.batmanmoviesapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import org.msfox.batmanmoviesapp.api.NetworkResponseAdapterFactory
import org.msfox.batmanmoviesapp.api.SearchMovies
import org.msfox.batmanmoviesapp.db.AppDb
import org.msfox.batmanmoviesapp.db.SearchMoviesDao
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideSearchMovies(): SearchMovies {
        return Retrofit.Builder()
            .baseUrl("http://www.omdbapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .build()
            .create(SearchMovies::class.java)
    }
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
    fun provideVehicleDao(db: AppDb): SearchMoviesDao {
        return db.searchMoviesDao()
    }

}