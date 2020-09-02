package org.msfox.batmanmoviesapp.api

import com.google.gson.Gson
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.msfox.batmanmoviesapp.model.Description
import org.msfox.batmanmoviesapp.utils.JsonString

@RunWith(JUnit4::class)
class DescriptionJsonFormatTest {

    @Test
    fun movieJsonToObject(){
        val json = JsonString.getJsonAsString("description-movie.json")
        val gson = Gson()
        val data = gson.fromJson(json, Description::class.java)

        Assert.assertEquals("tt0372784", data.imdbID)
        Assert.assertEquals("movie", data.type)
        Assert.assertEquals("Batman Begins", data.title)
    }

    @Test
    fun seriesJsonToObject(){
        val json = JsonString.getJsonAsString("description-series.json")
        val gson = Gson()
        val data = gson.fromJson(json, Description::class.java)

        Assert.assertEquals("tt0103359", data.imdbID)
        Assert.assertEquals("series", data.type)
        Assert.assertEquals("Batman: The Animated Series", data.title)
    }

    @Test
    fun gameJsonToObject(){
        val json = JsonString.getJsonAsString("description-game.json")
        val gson = Gson()
        val data = gson.fromJson(json, Description::class.java)

        Assert.assertEquals("tt1149317", data.imdbID)
        Assert.assertEquals("game", data.type)
        Assert.assertEquals("Lego Batman: The Videogame", data.title)
    }
}