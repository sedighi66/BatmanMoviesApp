package org.msfox.batmanmoviesapp.api

import com.google.gson.Gson
import okio.Okio
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.msfox.batmanmoviesapp.utils.JsonString.getJsonAsString


@RunWith(JUnit4::class)
class JsonFormatTest {

    @Test
    fun json(){
        val json = getJsonAsString("list-page1.json")
        val gson = Gson()
        val data = gson.fromJson(json, SearchMovies::class.java)


        val state = data.response
        val first = data.movies[0]
        val last = data.movies[9]

        Assert.assertEquals("True", state)

        Assert.assertEquals(10, data.movies.count())

        Assert.assertEquals("Batman Begins", first.title)
        Assert.assertEquals("2005", first.year)
        Assert.assertEquals("tt0372784", first.imdbID)
        Assert.assertEquals("movie", first.type)
        Assert.assertEquals("https://m.media-amazon.com/images/M/MV5BOTY4YjI2N2MtYmFlMC00ZjcyLTg3YjEtMDQyM2ZjYzQ5YWFkXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg",
            first.poster)


        Assert.assertEquals("Batman: The Dark Knight Returns, Part 1", last.title)
        Assert.assertEquals("2012", last.year)
        Assert.assertEquals("tt2313197", last.imdbID)
        Assert.assertEquals("movie", last.type)
        Assert.assertEquals("https://m.media-amazon.com/images/M/MV5BMzIxMDkxNDM2M15BMl5BanBnXkFtZTcwMDA5ODY1OQ@@._V1_SX300.jpg",
            last.poster)


    }
}