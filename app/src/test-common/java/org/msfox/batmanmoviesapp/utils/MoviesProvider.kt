package org.msfox.batmanmoviesapp.utils

import com.google.gson.Gson
import org.msfox.batmanmoviesapp.api.SearchMovies
import org.msfox.batmanmoviesapp.model.Movie

object MoviesProvider {

    fun getMoviesWithQueryBatmanPage1(): SearchMovies{
        val json = JsonString.getJsonAsString("list-page1.json")
        val gson = Gson()
        return gson.fromJson(json, SearchMovies::class.java)
    }

    fun get10Movies(): List<Movie> =
        listOf(Movie(1L, "t1","", "id1", "", ""),
            Movie(2L,"t2","", "id2", "", ""),
            Movie(3L,"t3","", "id3", "", ""),
            Movie(4L,"t4","", "id4", "", ""),
            Movie(5L,"t5","", "id5", "", ""),
            Movie(6L,"t6","", "id6", "", ""),
            Movie(7L,"t7","", "id7", "", ""),
            Movie(8L,"t8","", "id8", "", ""),
            Movie(9L, "t9","", "id9", "", ""),
            Movie(10L, "t10","", "id10", "", ""))
}