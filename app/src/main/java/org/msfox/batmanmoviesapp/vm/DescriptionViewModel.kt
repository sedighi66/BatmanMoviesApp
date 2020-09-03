package org.msfox.batmanmoviesapp.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import org.msfox.batmanmoviesapp.model.Description
import org.msfox.batmanmoviesapp.model.Movie
import org.msfox.batmanmoviesapp.repository.DescriptionRepository
import org.msfox.batmanmoviesapp.repository.Resource
import org.msfox.batmanmoviesapp.repository.SearchMoviesRepository
import javax.inject.Inject

class DescriptionViewModel @Inject constructor(
    private val repo: DescriptionRepository): ViewModel()  {

    fun getDescription(imdbId: String): LiveData<Resource<Description>> {
        return repo.getDescription(imdbId)
    }

    fun loadData() = repo.loadData()
}