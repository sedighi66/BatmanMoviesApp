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

    fun getMoviesWithQueryBatmanPage2(): SearchMovies{
        val json = JsonString.getJsonAsString("list-page2.json")
        val gson = Gson()
        return gson.fromJson(json, SearchMovies::class.java)
    }
}