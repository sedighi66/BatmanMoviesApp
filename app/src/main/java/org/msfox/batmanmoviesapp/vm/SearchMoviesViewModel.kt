package org.msfox.batmanmoviesapp.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import org.msfox.batmanmoviesapp.model.Movie
import org.msfox.batmanmoviesapp.repository.Resource
import org.msfox.batmanmoviesapp.repository.SearchMoviesRepository
import javax.inject.Inject

class SearchMoviesViewModel @Inject constructor(private val repo: SearchMoviesRepository): ViewModel()  {

    init {
        //we assume the capability to search for movies from api.
        //but for this test project in wasn't in the requirement.
        //so we just hard code query to batman.
        repo.setQuery("batman")
    }

    fun loadData() = repo.loadData()
    fun getMovies(): LiveData<Resource<List<Movie>>> = repo.getMovies()
}