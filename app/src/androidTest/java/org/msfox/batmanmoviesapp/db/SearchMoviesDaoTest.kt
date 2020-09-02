package org.msfox.batmanmoviesapp.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.msfox.batmanmoviesapp.model.QueryIds
import org.msfox.batmanmoviesapp.utils.MoviesProvider

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class SearchMoviesDaoTest : AppDbTest() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val searchMoviesDao: SearchMoviesDao
        get() = db.searchMoviesDao()

    @Test
    fun insertAndGetMoviesByIds() = runBlocking {
        //insert empty list
        searchMoviesDao.insertMovies(listOf())
        var getMovie = searchMoviesDao.getMoviesByIds(listOf())
        Assert.assertEquals(0, getMovie.count())

        //insert
        val movies = MoviesProvider.getMoviesWithQueryBatmanPage1().movies
        searchMoviesDao.insertMovies(movies)

        getMovie = searchMoviesDao.getMoviesByIds(movies.map { it.imdbID })
        Assert.assertEquals(10, getMovie.count())
        Assert.assertEquals(movies[0].title, getMovie[0].title)
        Assert.assertEquals(movies[1].title, getMovie[1].title)
        Assert.assertEquals(movies[2].title, getMovie[2].title)
        Assert.assertEquals(movies[3].title, getMovie[3].title)
        Assert.assertEquals(movies[4].title, getMovie[4].title)
        Assert.assertEquals(movies[5].title, getMovie[5].title)
        Assert.assertEquals(movies[6].title, getMovie[6].title)
        Assert.assertEquals(movies[7].title, getMovie[7].title)
        Assert.assertEquals(movies[8].title, getMovie[8].title)
        Assert.assertEquals(movies[9].title, getMovie[9].title)
    }

    @Test
    fun insertQueryIdsAndGet() = runBlocking {
        val imdbId = "1111"
        val query = "batman"

        //empty list for queryIds
        var queryIds = QueryIds(query, listOf())
        searchMoviesDao.insertQueryIds(queryIds)

        var movieIds = searchMoviesDao.getQueryIds(query)

        Assert.assertEquals(0, movieIds.imdbIds.count())

        //insert
        queryIds = QueryIds(query, listOf(imdbId))
        searchMoviesDao.insertQueryIds(queryIds)

        movieIds = searchMoviesDao.getQueryIds(query)

        Assert.assertEquals(1, movieIds.imdbIds.count())
        Assert.assertEquals(imdbId, movieIds.imdbIds.first())
    }

    @Test
    fun insertMoviesByQueryAndGetItPage1AndPage2AndRefresh() = runBlocking{

        val query = "batman"
        //when there is nothing in db yet, the db should return an empty list
        var getMovies = searchMoviesDao.getMovies(query)
        Assert.assertEquals(0, getMovies.count())

        //insert
        val moviesPage1 = MoviesProvider.getMoviesWithQueryBatmanPage1()

        searchMoviesDao.insertQueryAndMovies(query, moviesPage1.movies)
        getMovies = searchMoviesDao.getMovies(query)

        Assert.assertEquals(10, getMovies.count())
        Assert.assertEquals(moviesPage1.movies.first().title, getMovies.first().title)
        Assert.assertEquals(moviesPage1.movies.last().title, getMovies.last().title)

        val moviesPage2 = MoviesProvider.getMoviesWithQueryBatmanPage2()

        searchMoviesDao.insertQueryAndMovies(query, moviesPage2.movies)
        getMovies = searchMoviesDao.getMovies(query)

        Assert.assertEquals(20, getMovies.count())
        Assert.assertEquals(moviesPage1.movies.first().title, getMovies.first().title)
        Assert.assertEquals(moviesPage2.movies[0].title, getMovies[10].title)
        Assert.assertEquals(moviesPage2.movies.last().title, getMovies.last().title)

        //delete queries in case of refresh
        val deletedRows = searchMoviesDao.deleteQuery(query)
        getMovies = searchMoviesDao.getMovies(query)
        Assert.assertEquals(0, getMovies.count())
    }
}