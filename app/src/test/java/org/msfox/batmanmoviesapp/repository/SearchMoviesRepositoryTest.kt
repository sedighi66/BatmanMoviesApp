package org.msfox.batmanmoviesapp.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.msfox.batmanmoviesapp.api.MovieService
import org.msfox.batmanmoviesapp.api.NetworkResponse
import org.msfox.batmanmoviesapp.api.SearchMovies
import org.msfox.batmanmoviesapp.db.SearchMoviesDao
import org.msfox.batmanmoviesapp.model.Movie
import org.msfox.batmanmoviesapp.utils.MainCoroutineRule
import org.msfox.batmanmoviesapp.utils.MoviesProvider

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class SearchMoviesRepositoryTest {

    private lateinit var repo: SearchMoviesRepository
    private val dao = Mockito.mock(SearchMoviesDao::class.java)
    private val service = Mockito.mock(MovieService::class.java)

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        repo = SearchMoviesRepository(
            coroutineRule.getAppCoroutineDispatchers(),
            dao, service)
    }

    @Test
    fun `load movies in online mode`() = coroutineRule.runBlockingTest {

        val query = "batman"
        var page = 1
        val data = MutableLiveData<List<Movie>>()
        val list = MoviesProvider.getMoviesWithQueryBatmanPage1().movies
        val response = SearchMovies(list, "10", "True")
        Mockito.`when`(dao.deleteQuery(query)).thenReturn(1)

        Mockito.`when`(service.search(query, page)).thenReturn(NetworkResponse.Success(response))

        val vehicles = repo.setQuery("batman")
        Mockito.verify(dao).deleteQuery(query)

//     //   Mockito.verifyNoMoreInteractions(service)
//
//        val observer = mock<Observer<Resource<List<Vehicle>>>>()
//        vehicles.observeForever(observer)
//        Mockito.verifyNoMoreInteractions(service)
//        Mockito.verify(observer).onChanged(Resource.loading(null))
//
//        data.postValue(list)
//        Mockito.verify(observer).onChanged(Resource.success(list))
//        Mockito.verify(dao).getAll()
//        Mockito.verifyNoMoreInteractions(service)
//
//        list.add(createVehicle(1))
//        Mockito.verify(observer).onChanged(Resource.success(list))
//        Mockito.verify(dao).getAll()
//        Mockito.verifyNoMoreInteractions(service)
    }
}