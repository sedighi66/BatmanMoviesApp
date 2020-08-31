package org.msfox.batmanmoviesapp.db

import android.service.voice.VoiceInteractionSession
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.msfox.batmanmoviesapp.model.Movie
import org.msfox.batmanmoviesapp.model.QueryIds
import org.msfox.batmanmoviesapp.utils.MoviesProvider

/**
 * Created by mohsen on 04,July,2020
 */
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class SearchMoviesDaoTest : AppDbTest() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val searchMoviesDao: SearchMoviesDao
        get() = db.searchMoviesDao()

    @Test
    fun insertAndGetMoviesByIds() = runBlocking {
        //insert
        val movies = MoviesProvider.getMoviesWithQueryBatmanPage1().movies
        searchMoviesDao.insertMovies(movies)

        val getMovie = searchMoviesDao.getMoviesByIds(movies.map { it.imdbID })
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
        //insert
        val imdbId = "1111"
        val query = "batman"

        val queryIds = QueryIds(query, listOf(imdbId))
        searchMoviesDao.insertQueryIds(queryIds)

        val movieIds = searchMoviesDao.getQueryIds(query)

        Assert.assertEquals(1, movieIds.imdbIds.count())
        Assert.assertEquals(imdbId, movieIds.imdbIds.first())
    }

    @Test
    fun insertMoviesByQueryAndGetIt() = runBlocking{
        val query = "batman"
        val movies = MoviesProvider.getMoviesWithQueryBatmanPage1()

        searchMoviesDao.insertQueryIdsAndMovies(query, movies.movies)
        val getMovies = searchMoviesDao.getMoviesByQuery(query)

        Assert.assertEquals(10, getMovies.count())
        Assert.assertEquals(movies.movies.first().title, getMovies.first().title)
        Assert.assertEquals(movies.movies.last().title, getMovies.last().title)
    }
}