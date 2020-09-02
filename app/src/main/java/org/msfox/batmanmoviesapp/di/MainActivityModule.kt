package org.msfox.batmanmoviesapp.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import org.msfox.batmanmoviesapp.view.MainActivity

@Suppress("unused")
@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}
