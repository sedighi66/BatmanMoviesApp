/*
 * This file is created by Mohsen Sedighi with code name MSFox
 *
 * Copyright 8/8/20 4:45 PM belongs to Mohsen Sedighi from project BaManChallenge
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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