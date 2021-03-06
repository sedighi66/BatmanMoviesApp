package org.msfox.batmanmoviesapp.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import org.msfox.batmanmoviesapp.view.DescriptionActivity

@Suppress("unused")
@Module
abstract class DescriptionActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeDescriptionActivity(): DescriptionActivity
}
