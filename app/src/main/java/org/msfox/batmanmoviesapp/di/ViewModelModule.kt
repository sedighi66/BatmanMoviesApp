package org.msfox.batmanmoviesapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import org.msfox.batmanmoviesapp.vm.AppViewModelFactory
import org.msfox.batmanmoviesapp.vm.DescriptionViewModel
import org.msfox.batmanmoviesapp.vm.SearchMoviesViewModel

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SearchMoviesViewModel::class)
    abstract fun bindSearchMoviesViewModel(searchMoviesViewModel: SearchMoviesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DescriptionViewModel::class)
    abstract fun bindDescriptionViewModel(descriptionViewModel: DescriptionViewModel): ViewModel
}