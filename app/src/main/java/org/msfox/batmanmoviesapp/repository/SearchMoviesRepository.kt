package org.msfox.batmanmoviesapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.msfox.batmanmoviesapp.AppCoroutineDispatchers
import org.msfox.batmanmoviesapp.api.MovieService
import org.msfox.batmanmoviesapp.api.NetworkResponse
import org.msfox.batmanmoviesapp.api.SearchMovies
import org.msfox.batmanmoviesapp.db.SearchMoviesDao
import org.msfox.batmanmoviesapp.model.Movie
import org.msfox.batmanmoviesapp.utils.combineWith
import org.msfox.batmanmoviesapp.utils.toNextPage
import javax.inject.Inject

class SearchMoviesRepository @Inject constructor(
    private val dispatchers: AppCoroutineDispatchers,
    private val dao: SearchMoviesDao,
    private val service: MovieService
): BaseRepository<List<Movie>, SearchMovies>(dispatchers) {

    private var query = ""
    private var nextPage = 1
    private val movies = MutableLiveData<Resource<List<Movie>>>()

    init {
        movies.combineWith(super.liveDataResult()) { list, next ->
            val items = mutableListOf<Movie>().apply {
                addAll(list?.data ?: emptyList())
                addAll(next?.data ?: emptyList())
            }
            Resource(next?.status ?: list!!.status, items as List<Movie>, next?.message)
        }
    }

    fun getNextPage(): LiveData<Resource<List<Movie>>> {
        super.loadNextPage()
        return movies
    }

    fun getMovies() = movies as LiveData<Resource<List<Movie>>>

    fun setQuery(query: String){
        this.query = query
        super.fetchNetwork()
    }

    fun getQuery() = query

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