package org.msfox.batmanmoviesapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.msfox.batmanmoviesapp.AppCoroutineDispatchers
import org.msfox.batmanmoviesapp.api.MovieService
import org.msfox.batmanmoviesapp.api.NetworkResponse
import org.msfox.batmanmoviesapp.api.SearchMovies
import org.msfox.batmanmoviesapp.db.DescriptionDao
import org.msfox.batmanmoviesapp.db.SearchMoviesDao
import org.msfox.batmanmoviesapp.model.Description
import org.msfox.batmanmoviesapp.model.Movie
import javax.inject.Inject

class DescriptionRepository @Inject constructor(
    private val dispatchers: AppCoroutineDispatchers,
    private val dao: DescriptionDao,
    private val service: MovieService
): BaseRepository<Description, Description>(dispatchers) {

    private lateinit var imdbId: String

    fun getDescription(imdbId: String): LiveData<Resource<Description>> {
        this.imdbId = imdbId
        fetchNetwork()
        return liveDataResult()
    }

    override suspend fun createCall(): NetworkResponse<Description, Any> =
        service.description(imdbId)

    override fun successRequestToResult(requestType: Resource<Description>): Resource<Description> =
        requestType

    override suspend fun saveCallResult(item: Resource<Description>) =
        dao.insert(item.data!!)

    override suspend fun loadFromDb(): Description =
        dao.getDescription(imdbId)
}