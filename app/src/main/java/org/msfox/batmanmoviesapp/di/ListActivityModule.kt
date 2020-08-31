package org.msfox.batmanmoviesapp.di

/**
 * Created by mohsen on 04,July,2020
 */


import dagger.Module
import dagger.android.ContributesAndroidInjector
import org.msfox.batmanmoviesapp.MainActivity

@Suppress("unused")
@Module
abstract class ListActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeListActivity(): MainActivity
}
