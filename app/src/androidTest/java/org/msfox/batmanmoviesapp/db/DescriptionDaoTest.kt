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
class DescriptionDaoTest : AppDbTest() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val descriptionDao: DescriptionDao
        get() = db.descriptionDao()

    @Test
    fun insertAndGetDescriptionByIds() = runBlocking {
        //insert
        val imdbId = "tt0372784"
        val description = MoviesProvider.getDescriptionPage()
        descriptionDao.insert(description)

        val getDescription = descriptionDao.getDescription(imdbId)

        Assert.assertEquals(description.title, getDescription.title)
        Assert.assertEquals(description.year, getDescription.year)
        Assert.assertEquals(description.actors, getDescription.actors)
        Assert.assertEquals(description.imdbID, getDescription.imdbID)
        Assert.assertEquals(description.director, getDescription.director)
        Assert.assertEquals(description.ratings, getDescription.ratings)
    }
}