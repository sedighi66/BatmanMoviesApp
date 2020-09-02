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
    fun json(){
        val json = JsonString.getJsonAsString("description.json")
        val gson = Gson()
        val data = gson.fromJson(json, Description::class.java)


        val metacriticValue = data.ratings[2]

        Assert.assertEquals("True", data.response)
        Assert.assertEquals("tt0372784", data.imdbID)

        Assert.assertEquals(3, data.ratings.count())

        Assert.assertEquals("Batman Begins", data.title)
        Assert.assertEquals("2005", data.year)
        Assert.assertEquals("Christian Bale, Michael Caine, Liam Neeson, Katie Holmes", data.actors)

        Assert.assertEquals(metacriticValue.source, "Metacritic")
        Assert.assertEquals(metacriticValue.value, "70/100")
    }
}