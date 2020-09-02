package org.msfox.batmanmoviesapp.di

import dagger.Module

@Module(includes = [MainActivityModule::class, DescriptionActivityModule::class])
abstract class ActivitiesModule