package org.msfox.batmanmoviesapp.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import org.msfox.batmanmoviesapp.api.NetworkResponseAdapterFactory
import org.msfox.batmanmoviesapp.api.SearchMovies
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule() {

    @Singleton
    @Provides
    fun provideSearchMovies(): SearchMovies {
        return Retrofit.Builder()
            .baseUrl("https://dev.api.baman.club/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .build()
            .create(SearchMovies::class.java)
    }
    @Provides
    fun provideContext(application: Application): Context{
        return application.applicationContext
    }

}