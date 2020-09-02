package org.msfox.batmanmoviesapp.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import org.msfox.batmanmoviesapp.ui.DescriptionActivity
import org.msfox.batmanmoviesapp.ui.MainActivity

@Suppress("unused")
@Module
abstract class DescriptionActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeDescriptionActivity(): DescriptionActivity
}
