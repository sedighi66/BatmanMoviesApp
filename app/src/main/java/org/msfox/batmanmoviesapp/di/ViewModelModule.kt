package org.msfox.batmanmoviesapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import org.msfox.batmanmoviesapp.vm.AppViewModelFactory
import org.msfox.batmanmoviesapp.vm.SearchMoviesViewModel

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SearchMoviesViewModel::class)
    abstract fun bindListViewModel(listViewModel: SearchMoviesViewModel): ViewModel
}