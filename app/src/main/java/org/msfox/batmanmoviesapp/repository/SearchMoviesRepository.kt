package org.msfox.batmanmoviesapp.repository

import org.msfox.batmanmoviesapp.AppCoroutineDispatchers
import org.msfox.batmanmoviesapp.api.MovieService
import org.msfox.batmanmoviesapp.api.NetworkResponse
import org.msfox.batmanmoviesapp.api.SearchMovies
import org.msfox.batmanmoviesapp.db.SearchMoviesDao
import org.msfox.batmanmoviesapp.model.Movie
import org.msfox.batmanmoviesapp.utils.toNextPage
import javax.inject.Inject

class SearchMoviesRepository @Inject constructor(
    private val dispatchers: AppCoroutineDispatchers,
    private val dao: SearchMoviesDao,
    private val service: MovieService
): BaseRepository<List<Movie>, SearchMovies>(dispatchers) {

    var query = "batman"
    private var nextPage = 1

    fun getList() = liveDataResult()
    fun getNextPage() = loadNextPage()

    override suspend fun createCall(): NetworkResponse<SearchMovies, Any> =
        service.search(query, nextPage)

    override fun successRequestToResult(requestType: Resource<SearchMovies>): Resource<List<Movie>> =
        Resource.success(requestType.data?.movies)

    override suspend fun saveCallResult(item: Resource<List<Movie>>) =
        dao.insertQueryAndMovies(query, item.data?: listOf())

    override suspend fun loadFromDb(): List<Movie> {
        return dao.getMovies(query).also {
            nextPage = it.toNextPage(MovieService.OFFSET)
        }
    }

    override suspend fun deleteDb() = dao.deleteQuery(query)
}